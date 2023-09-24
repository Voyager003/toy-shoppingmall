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

    /* 상품 등록 */
    @PostMapping("/products/register")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> registerItem(@RequestPart(name = "request") @Valid ItemRequest request,
                                          @RequestPart(name = "image", required = false) MultipartFile imgFile) throws IOException {
        Long itemId = itemService.registerItem(request, imgFile);
        return ResponseEntity.ok(itemId);
    }

    /* 상품 단건 조회 */
    @GetMapping("/products/{itemId}")
    public ResponseEntity getItem(@PathVariable Long itemId) throws Throwable {
        return ResponseEntity.ok(itemService.findItem(itemId));
    }

    /* 페이징 조회*/
    @GetMapping("/products")
    public ItemPageDto getItems(@PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return ItemPageDto.of(itemService.findItems(pageable));
    }

    /* 상품 정보 수정 */
    @PutMapping("/products/{itemId}")
    @PreAuthorize("hasRole('SELLER')")
    public Long updateItem(@PathVariable Long itemId, ItemRequest request) throws Throwable {
        return itemService.updateItem(itemId, request);
    }

    /* 상품 정보 삭제 */
    @DeleteMapping("/products/{itemId}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity deleteItem(@PathVariable Long itemId) throws Throwable {
        itemService.deleteItem(itemId);
        return ResponseEntity.ok().build();
    }
}
