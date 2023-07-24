package toy.shoppingmall.domain.order.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toy.shoppingmall.domain.order.domain.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
