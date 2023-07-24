package toy.shoppingmall.domain.item.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.shoppingmall.domain.item.domain.Item;

@Getter
@NoArgsConstructor
public class ItemRequest {

    private String name;
    private int price;
    private int stockQuantity;
}
