package toy.shoppingmall.domain.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;
import toy.shoppingmall.domain.item.domain.Item;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
public class ItemPageDto {

    private List<ItemDto> elements;

    private long totalElements;

    private int currentPage;

    private int totalPages;

    public static ItemPageDto of(Page<Item> itemPage) {
        return ItemPageDto.builder()
                .elements(itemPage.getContent().stream().map(ItemDto::of).collect(Collectors.toList()))
                .totalElements(itemPage.getTotalElements())
                .totalPages(itemPage.getTotalPages())
                .currentPage(itemPage.getNumber())
                .build();
    }
}
