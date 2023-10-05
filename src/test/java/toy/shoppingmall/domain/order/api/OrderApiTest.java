package toy.shoppingmall.domain.order.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import toy.shoppingmall.domain.order.application.OrderService;
import toy.shoppingmall.domain.order.dto.OrderRequest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(controllers = OrderApi.class)
@WithMockUser
class OrderApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @Test
    @DisplayName("상품 주문 API 성공 테스트")
    void order_succesfully() throws Exception {

        /**
         * given : 주문을 위한 정보를 담은 OrderRequest 객체를 생성한다.
         * when : "/api/orders" Post 요청을 보낸다.
         * then : 주문이 성공적으로 이루어진다.
         */

        OrderRequest request = OrderRequest.builder()
                .userId(1L)
                .itemId(1L)
                .count(3)
                .build();

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("상품 주문 삭제 API 성공 테스트")
    void cancelOrder_succesfully() throws Exception {

        /**
         * given : 주문을 취소하기 위한 주문 ID를 담은 Long 객체를 생성한다.
         * when : "/api/orders/{id}"로 Delete 요청을 보낸다.
         * then : 주문 정보가 성공적으로 삭제된다.
         */

        Long orderId = 1L;

        mockMvc.perform(delete("/api/orders/" + orderId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }
}
