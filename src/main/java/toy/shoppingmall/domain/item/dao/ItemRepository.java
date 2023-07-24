package toy.shoppingmall.domain.item.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toy.shoppingmall.domain.item.domain.Item;

@Repository
public interface ItemRepository<T extends Item> extends JpaRepository<T, Long> {
}
