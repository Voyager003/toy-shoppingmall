package toy.shoppingmall.domain.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import toy.shoppingmall.domain.item.domain.Item;

@Getter
@Builder
@AllArgsConstructor
public class ItemDto {

    private long id;

    private String name;

    private int price;

    private int stockQuantity;

    private String imagePath;

    public static ItemDto of(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .stockQuantity(item.getStockQuantity())
                .imagePath(item.getImagePath())
                .build();
    }
}
