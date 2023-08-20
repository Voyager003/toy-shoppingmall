package toy.shoppingmall.domain.item.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import toy.shoppingmall.domain.item.application.ItemService;
import toy.shoppingmall.domain.item.dto.ItemRequest;


@RestController
@RequiredArgsConstructor
public class ItemApi {

    private final ItemService itemService;

    @PostMapping("/products/register")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> registerItem(@RequestBody @Valid ItemRequest request) {
        Long itemId = itemService.registerItem(request);
        return ResponseEntity.ok(itemId);
    }
}
