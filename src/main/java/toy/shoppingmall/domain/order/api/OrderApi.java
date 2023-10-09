package toy.shoppingmall.domain.order.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import toy.shoppingmall.domain.order.application.OrderService;
import toy.shoppingmall.domain.order.dto.OrderRequest;


@RestController
@RequiredArgsConstructor
public class OrderApi {

    private final OrderService orderService;

    @PostMapping("/api/orders")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity order(@RequestBody OrderRequest orderRequest) throws Throwable {
        orderService.order(orderRequest.getItemId(), orderRequest.getCount());
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/api/orders/{id}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity removeOrder(@PathVariable Long id) throws Throwable {
        orderService.cancelOrder(id);
        return ResponseEntity.ok().build();
    }
}
