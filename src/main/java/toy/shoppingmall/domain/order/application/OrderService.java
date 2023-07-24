package toy.shoppingmall.domain.order.application;

import lombok.RequiredArgsConstructor;
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

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Long order(Long userId, Long itemId, int count) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자 ID입니다."));

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

    @Transactional
    public void cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문을 찾을 수 없습니다. 유효하지 않은 주문 ID입니다."));

        order.cancelOrder();
    }
}
