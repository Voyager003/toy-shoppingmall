package toy.shoppingmall.domain.order.application;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toy.shoppingmall.domain.item.dao.ItemRepository;
import toy.shoppingmall.domain.item.domain.Book;
import toy.shoppingmall.domain.model.Address;
import toy.shoppingmall.domain.model.Role;
import toy.shoppingmall.domain.order.dao.OrderRepository;
import toy.shoppingmall.domain.order.domain.Order;
import toy.shoppingmall.domain.order.domain.OrderStatus;
import toy.shoppingmall.domain.user.dao.UserRepository;
import toy.shoppingmall.domain.user.domain.User;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;
    @Autowired UserRepository userRepository;
    @Autowired ItemRepository itemRepository;

    @AfterEach
    public void cleanup() {
        orderRepository.deleteAll();
        userRepository.deleteAll();
        itemRepository.deleteAll();
    }

    @Test
    @DisplayName("상품을 주문하고 상태 및 재고, 가격을 확인한다.")
    public void orderItemsTest() throws Exception {
        /**
         * given : 유저와 상품(Book)의 정보를 DB에 등록한다.
         * when : 상품을 주문한다.
         * then : 주문의 상태, 주문의 수량, 주문의 총 가격, 상품의 재고를 확인한다.
         */

        User user = registerUser();
        Book book = registerItem("kim", 1000, 5);

        Long orderId = orderService.order(user.getId(), book.getId(), 3);

        Order getOrder = orderRepository.findById(orderId).get();

        assertEquals(OrderStatus.ORDER, getOrder.getStatus());
        assertEquals(1, getOrder.getOrderItems().size());
        assertEquals(1000 * 3, getOrder.getTotalPrice());
        assertEquals(2, book.getStockQuantity());
    }

    @Test
    @DisplayName("재고수량을 초과한 경우, 예외가 발생한다.")
    public void OverstockExceptionTest() throws Exception {
        /**
         * given : 유저와 상품(Book)의 정보를 DB에 등록한다.
         * when : 재고(5)를 초과한 주문(6)을 요구한다.
         * then : 예외를 발생시킨다.
         */

        User user = registerUser();
        Book book = registerItem("kim", 1000, 5);

        Assertions.assertThrows(IllegalStateException.class, () -> {
            orderService.order(user.getId(), book.getId(), 6);
        });
    }

    @Test
    @DisplayName("주문을 취소한다.")
    public void cancelOrderTest() throws Exception {
        /**
         * given : 유저와 상품(Book)의 정보를 DB에 등록한다.
         * when : 주문을 취소한다.(cancelOrder)
         * then : 취소한 뒤의 상태는 'CANCEL'이 되며, 상품의 재고를 원복한다.
         */

        User user = registerUser();
        Book book = registerItem("kim", 1000, 5);

        Long orderId = orderService.order(user.getId(), book.getId(), 3);

        orderService.cancelOrder(orderId);

        Order getOrder = orderRepository.findById(orderId).get();

        assertEquals(OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals(5, book.getStockQuantity());
    }

    private User registerUser() {
        Address address = new Address("한국", "서울");
        User user = User.builder()
                .email("ex@gmail.com")
                .password("123")
                .address(address)
                .role(Role.ROLE_SELLER)
                .build();
        userRepository.save(user);
        return user;
    }

    private Book registerItem(String name, int price, int stockQuantity) {
        Book book = Book.builder()
                .author("kim")
                .name(name)
                .price(price)
                .stockQuantity(stockQuantity)
                .build();
        itemRepository.save(book);
        return book;
    }


}