package toy.shoppingmall.domain.item.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import toy.shoppingmall.domain.item.application.ItemService;
import toy.shoppingmall.domain.item.dto.ItemRequest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemApi.class)
@WithMockUser
class ItemApiTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @MockBean ItemService itemService;

    @Test
    @DisplayName("상품 등록 성공 케이스")
    void testRegisterItem_success() throws Exception {
        /**
         *  given : 상품명, 가격, 재고, 카테고리가 주어진다.
         *  when : "/products/register" Post요청을 보내고
         *  then : Request의 validation이 조건에 맞다면, STATUS 200을 반환한다.
         */

        ItemRequest request = ItemRequest.builder()
                .name("상품명")
                .price(10000)
                .stockQuantity(5)
                .category("book")
                .categoryDetail("Author Name")
                .build();

        mockMvc.perform(post("/products/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("상품 등록 실패 케이스")
    void testRegisterItem2() throws Exception {
        /**
         *  given : 상품명, 가격, 재고, 카테고리가 주어진다.
         *  when : "/products/register" Post요청을 보내고
         *  then : Request의 validation이 조건에 맞지 않다면, STATUS 400을 반환한다.
         */

        ItemRequest request = ItemRequest.builder()
                .name("상품명")
                .price(100)
                .stockQuantity(0)
                .category("book")
                .categoryDetail("Author Name")
                .build();

        mockMvc.perform(post("/products/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }
}
