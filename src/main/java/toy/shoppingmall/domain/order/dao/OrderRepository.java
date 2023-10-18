package toy.shoppingmall.domain.order.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.shoppingmall.domain.order.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
