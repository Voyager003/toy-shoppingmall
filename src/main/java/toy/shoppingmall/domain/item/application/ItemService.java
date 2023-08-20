package toy.shoppingmall.domain.item.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.shoppingmall.domain.item.dao.ItemRepository;
import toy.shoppingmall.domain.item.domain.Album;
import toy.shoppingmall.domain.item.domain.Book;
import toy.shoppingmall.domain.item.domain.Item;
import toy.shoppingmall.domain.item.domain.Movie;
import toy.shoppingmall.domain.item.dto.ItemRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public Long registerItem(ItemRequest request) {
        Item newItem = createItem(request);
        if (newItem != null) {
            itemRepository.save(newItem);
        }
        return newItem.getId();
    }

    private Item createItem(ItemRequest request) {
        String category = request.getCategory();
        String categoryDetail = request.getCategoryDetail();

        switch (category) {
            case "book":
                return Book.builder()
                        .name(request.getName())
                        .price(request.getPrice())
                        .stockQuantity(request.getStockQuantity())
                        .author(categoryDetail)
                        .build();
            case "album":
                return Album.builder()
                        .name(request.getName())
                        .price(request.getPrice())
                        .stockQuantity(request.getStockQuantity())
                        .artist(categoryDetail)
                        .build();
            case "movie":
                return Movie.builder()
                        .name(request.getName())
                        .price(request.getPrice())
                        .stockQuantity(request.getStockQuantity())
                        .director(categoryDetail)
                        .build();
            default:
                return null;
        }
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }
}
