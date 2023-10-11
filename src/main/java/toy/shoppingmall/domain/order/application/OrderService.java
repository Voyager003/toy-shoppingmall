package toy.shoppingmall.domain.order.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.shoppingmall.domain.item.dao.ItemRepository;
import toy.shoppingmall.domain.item.domain.Item;
import toy.shoppingmall.domain.model.Delivery;
import toy.shoppingmall.domain.order.dao.OrderRepository;
import toy.shoppingmall.domain.order.domain.Order;
import toy.shoppingmall.domain.order.domain.OrderItem;
import toy.shoppingmall.domain.user.dao.UserRepository;
import toy.shoppingmall.domain.user.domain.User;
import toy.shoppingmall.global.security.UserDetailsImpl;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    /* 상품 주문 */
    @Transactional
    public Long order(Long itemId, int count) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + userId));

        Optional<Item> optionalItem = itemRepository.findById(itemId);
        Item item = optionalItem.orElseThrow(() -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다. 유효하지 않은 상품 ID입니다."));

        Delivery delivery = Delivery.builder()
                .address(user.getAddress())
                .build();

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        Order order = Order.createOrder(user, delivery, orderItem);

        orderRepository.save(order);

        return order.getId();
    }

    /* 주문 조회 */
    public List<Order> getOrderList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + userId));

        return orderRepository.findAllByUserId(user.getId());
    }

    /* 주문 취소 */
    @Transactional
    public void cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문을 찾을 수 없습니다. 유효하지 않은 주문 ID입니다."));

        order.cancelOrder();
    }
}
