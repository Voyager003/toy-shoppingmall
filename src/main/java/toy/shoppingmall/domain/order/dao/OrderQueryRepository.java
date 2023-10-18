package toy.shoppingmall.domain.order.dao;

import toy.shoppingmall.domain.order.domain.Order;

import java.util.List;

public interface OrderQueryRepository {

    List<Order> findOrderListByUserId(Long userId);
}
