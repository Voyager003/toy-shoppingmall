package toy.shoppingmall.domain.item.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.shoppingmall.domain.item.dao.ItemRepository;
import toy.shoppingmall.domain.item.domain.Item;
import toy.shoppingmall.domain.item.dto.ItemRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void registerItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }
}
