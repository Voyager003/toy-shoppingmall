package toy.shoppingmall.domain.order.application;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import toy.shoppingmall.domain.item.dao.ItemRepository;
import toy.shoppingmall.domain.item.domain.Book;
import toy.shoppingmall.domain.model.Address;
import toy.shoppingmall.domain.user.domain.Authority;
import toy.shoppingmall.domain.user.domain.Role;
import toy.shoppingmall.domain.order.dao.OrderRepository;
import toy.shoppingmall.domain.order.domain.Order;
import toy.shoppingmall.domain.order.domain.OrderStatus;
import toy.shoppingmall.domain.user.dao.UserRepository;
import toy.shoppingmall.domain.user.domain.User;
import toy.shoppingmall.global.security.UserDetailsImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;
    @Autowired UserRepository userRepository;
    @Autowired ItemRepository itemRepository;

    @Test
    @DisplayName("상품을 주문하고 상태 및 재고, 가격을 확인한다.")
    public void orderItemsTest() throws Exception {
        /**
         * given : 유저와 상품의 정보를 등록한다.
         * when : 상품을 주문한다.
         * then : 주문의 상태, 주문의 수량, 주문의 총 가격, 상품의 재고를 확인한다.
         */

        registerUser();
        Book book = registerItem("kim", 1000, 5);
        Long orderId = orderService.order(book.getId(), 3);

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
         * given : 유저와 상품(Book)의 정보를 등록한다.
         * when : 재고(5)를 초과한 주문(6)을 요구한다.
         * then : 예외를 발생시킨다.
         */

        registerUser();
        Book book = registerItem("kim", 1000, 5);

        Assertions.assertThrows(IllegalStateException.class, () -> {
            orderService.order(book.getId(), 6);
        });
    }

    @Test
    @DisplayName("상품을 등록하고, 상품 조회 후 주문 목록의 크기를 확인한다.")
    void getOrderList_Successfully() {
        /**
         * given : 유저와 상품(Book)의 정보를 등록한다.
         * when : 상품을 각 2개, 1개씩 2번 주문한다.
         * then : 생성된 주문 목록은 2개이다.
         */

        registerUser();
        Book book1 = registerItem("kim", 1000, 5);
        Book book2 = registerItem("rim", 5000, 3);

        orderService.order(book1.getId(), 2);
        orderService.order(book2.getId(), 2);
        List<Order> orderList = orderService.getOrderList();

        assertEquals(2, orderList.size());
    }

    @Test
    @DisplayName("주문을 취소한다.")
    public void cancelOrderTest() throws Exception {
        /**
         * given : 유저와 상품(Book)의 정보를 DB에 등록한다.
         * when : 주문을 취소한다.(cancelOrder)
         * then : 취소한 뒤의 상태는 'CANCEL'이 되며, 상품의 재고를 원복한다.
         */

        registerUser();
        Book book = registerItem("kim", 1000, 5);

        Long orderId = orderService.order(book.getId(), 3);

        orderService.cancelOrder(orderId);

        Order getOrder = orderRepository.findById(orderId).get();

        assertEquals(OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals(5, book.getStockQuantity());
    }

    private void registerUser() {
        Address address = new Address("한국", "서울");

        Set<Authority> authorities = new HashSet<>();
        Authority authority = Authority.builder().name(Role.ROLE_SELLER).build();
        User user = User.builder()
                .email("ex@gmail.com")
                .password("123")
                .address(address)
                .roles(authorities)
                .build();
        userRepository.save(user);

        Authentication mockAuth = Mockito.mock(Authentication.class);

        UserDetailsImpl userDetails = new UserDetailsImpl(user.getId(), user.getEmail(), user.getPassword(), null);
        Mockito.when(mockAuth.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.getContext().setAuthentication(mockAuth);
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