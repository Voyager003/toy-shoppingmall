package toy.shoppingmall.domain.item.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemRequest {

    @NotBlank
    private String name;

    @Min(value = 1000, message = "최소 1000원 이상의 가격이어야 합니다.")
    @Max(value = 50000, message = "최대 50000원 이하의 가격이어야 합니다.")
    private int price;

    @Min(value = 1, message = "재고는 최소 1개 이상이어야 합니다.")
    @Max(value = 10, message = "재고는 최대 10개 이하이어야 합니다.")
    private int stockQuantity;

    private String category;
    @NotBlank
    private String categoryDetail;

    @Builder
    public ItemRequest(String name, int price, int stockQuantity, String category, String categoryDetail) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.categoryDetail = categoryDetail;
    }
}
