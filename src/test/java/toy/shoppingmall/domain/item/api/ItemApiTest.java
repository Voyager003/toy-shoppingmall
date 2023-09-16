package toy.shoppingmall.domain.item.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import toy.shoppingmall.domain.item.application.ItemService;
import toy.shoppingmall.domain.item.dto.ItemRequest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemApi.class)
@WithMockUser
class ItemApiTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @MockBean ItemService itemService;

    @Test
    @DisplayName("상품 등록 성공 케이스")
    void registerItem_success() throws Exception {
        /**
         *  given : 상품명, 가격, 재고, 카테고리, 이미지(multiPart)가 주어진다.
         *  when : "/products/register" Post 요청을 보내서
         *  then : 유효성 검증에 통과한다면, STATUS 200을 반환한다.
         */

        String itemRequestJson = objectMapper.writeValueAsString(
                ItemRequest.builder()
                        .name("상품명")
                        .price(10000)
                        .stockQuantity(5)
                        .category("book")
                        .categoryDetail("Author Name")
                        .build()
        );

        MockMultipartFile ItemRequest = new MockMultipartFile(
                "request",
                "request.json",
                MediaType.APPLICATION_JSON_VALUE,
                itemRequestJson.getBytes()
        );

        MockMultipartFile MultipartFile = new MockMultipartFile(
                "image",
                "test.png",
                "image/png",
                "test image".getBytes()
        );

        mockMvc.perform(multipart("/products/register")
                        .file(MultipartFile)
                        .file(ItemRequest)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("상품 등록 실패 케이스")
    void registerItem_fail() throws Exception {
        /**
         *  given : 유효성 검증이 벗어난 가격, 재고가 주어진다.
         *  when : "/products/register" Post 요청을 보내서
         *  then : 유효성 검증에 통과하지 못하면, STATUS 400을 반환한다.
         */

        String itemRequestJson = objectMapper.writeValueAsString(
                ItemRequest.builder()
                        .name("상품명")
                        .price(60000)
                        .stockQuantity(11)
                        .category("book")
                        .categoryDetail("Author Name")
                        .build()
        );

        MockMultipartFile ItemRequest = new MockMultipartFile(
                "request",
                "request.json",
                MediaType.APPLICATION_JSON_VALUE,
                itemRequestJson.getBytes()
        );

        MockMultipartFile MultipartFile = new MockMultipartFile(
                "image",
                "test.png",
                "image/png",
                "test image".getBytes()
        );

        mockMvc.perform(multipart("/products/register")
                        .file(MultipartFile)
                        .file(ItemRequest)
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
