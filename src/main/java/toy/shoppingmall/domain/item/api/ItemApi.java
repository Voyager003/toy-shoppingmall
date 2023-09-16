package toy.shoppingmall.domain.item.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import toy.shoppingmall.domain.item.application.ItemService;
import toy.shoppingmall.domain.item.dto.ItemPageDto;
import toy.shoppingmall.domain.item.dto.ItemRequest;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
public class ItemApi {

    private final ItemService itemService;

    @GetMapping("/products/{itemId}")
    public ResponseEntity getItem(@PathVariable Long itemId) throws Throwable {
        return ResponseEntity.ok(itemService.findItem(itemId));
    }

    @GetMapping("/products")
    public ItemPageDto getItems(@PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return ItemPageDto.of(itemService.findItems(pageable));
    }

    @PostMapping("/products/register")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> registerItem(@RequestPart(name = "request") @Valid ItemRequest request,
                                          @RequestPart(name = "image", required = false) MultipartFile imgFile) throws IOException {
        Long itemId = itemService.registerItem(request, imgFile);
        return ResponseEntity.ok(itemId);
    }
}
