package toy.shoppingmall.domain.item.domain;

import jakarta.persistence.*;
import lombok.Getter;

/**
 * 이름, 가격, 재고를 가짐
 */

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;
}
