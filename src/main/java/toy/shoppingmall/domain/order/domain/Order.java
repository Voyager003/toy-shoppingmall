package toy.shoppingmall.domain.order.domain;

import jakarta.persistence.*;
import lombok.*;
import toy.shoppingmall.domain.model.Delivery;
import toy.shoppingmall.domain.model.DeliveryStatus;
import toy.shoppingmall.domain.user.domain.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 상품(product)을 주문한 유저와 배송 정보, 주문 날짜, 상태를 가짐
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Builder
    private Order(User user, Delivery delivery, List<OrderItem> orderItems, LocalDateTime orderDate, OrderStatus orderStatus) {
        this.user = user;
        this.delivery = delivery;
        this.orderItems = orderItems;
        this.orderDate = orderDate;
        this.status = orderStatus;

        Delivery.builder().order(this).build();
        OrderItem.builder().order(this).build();
    }

    public static Order createOrder(User user, Delivery delivery, OrderItem... orderItems) {
        return builder()
                .user(user)
                .delivery(delivery)
                .orderItems(Arrays.asList(orderItems))
                .orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.ORDER)
                .build();
    }

    public void cancelOrder() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송 완료된 상품은 취소가 불가능합니다.");
        }

        this.status = OrderStatus.CANCEL;
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    public int getTotalPrice() {
        return orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
    }
}
