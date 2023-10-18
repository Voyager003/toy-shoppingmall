package toy.shoppingmall.domain.order.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import toy.shoppingmall.domain.order.application.OrderService;
import toy.shoppingmall.domain.order.domain.Order;
import toy.shoppingmall.domain.order.dto.OrderRequest;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class OrderApi {

    private final OrderService orderService;

    /* 상품 주문 */
    @PostMapping("/api/orders")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<?> order(@RequestBody OrderRequest orderRequest) throws Throwable {
        orderService.order(orderRequest.getItemId(), orderRequest.getCount());
        return ResponseEntity.ok().build();
    }

    /* 상품 조회 */
    @GetMapping("/api/orders")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<?> getOrder() throws Throwable {
        List<Order> orderList = orderService.getOrderList();
        return ResponseEntity.ok(orderList);
    }

    /* 주문 취소 */
    @DeleteMapping("/api/orders/{id}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<?> removeOrder(@PathVariable Long id) throws Throwable {
        orderService.cancelOrder(id);
        return ResponseEntity.ok().build();
    }
}
