package toy.shoppingmall.domain.item.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.shoppingmall.domain.item.domain.Item;

@Getter
@NoArgsConstructor
public class ItemResponse {

    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private String imagePath;

    public ItemResponse(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
        this.stockQuantity = item.getStockQuantity();
        this.imagePath = item.getImagePath();
    }
}
