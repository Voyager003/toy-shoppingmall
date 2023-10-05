package toy.shoppingmall.domain.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderRequest {

    private Long userId;
    private Long itemId;
    private int count;

    @Builder
    private OrderRequest(Long userId, Long itemId, int count) {
        this.userId = userId;
        this.itemId = itemId;
        this.count = count;
    }
}
