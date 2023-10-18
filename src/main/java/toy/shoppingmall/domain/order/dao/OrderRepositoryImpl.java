package toy.shoppingmall.domain.order.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toy.shoppingmall.domain.order.domain.Order;

import java.util.List;

import static toy.shoppingmall.domain.order.domain.QOrder.order;
import static toy.shoppingmall.domain.user.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    /* select from order  */
    @Override
    public List<Order> findOrderListByUserId(Long userId) {
        return jpaQueryFactory
                .selectFrom(order)
                .join(order.user, user)// user에 대한 fetch join
                .where(user.id.eq(userId))
                .fetch();
    }
}
