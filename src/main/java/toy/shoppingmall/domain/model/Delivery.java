package toy.shoppingmall.domain.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.shoppingmall.domain.order.domain.Order;


@Entity
@Getter
@NoArgsConstructor
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    @Builder
    private Delivery(Order order, Address address, DeliveryStatus deliveryStatus) {
        this.order = order;
        this.address = address;
        this.status = deliveryStatus;
    }
}
