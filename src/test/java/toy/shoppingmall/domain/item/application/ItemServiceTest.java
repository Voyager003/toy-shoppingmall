package toy.shoppingmall.domain.item.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import toy.shoppingmall.domain.item.dao.ItemRepository;
import toy.shoppingmall.domain.item.domain.Book;
import toy.shoppingmall.domain.item.dto.ItemRequest;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired ItemRepository itemRepository;

    @Autowired ItemService itemService;

    @Test
    @DisplayName("상품 등록 테스트")
    void itemRegisterTest() throws IOException {
        /**
         * given : Item(책)의 상품 명, 가격, 재고가 주어진다.
         * when : 상품 등록을 요청한다.
         * then : 요청한 상품 명, 가격, 재고를 저장하고 예상 값과 같은지 검증한다.
         */

        ItemRequest bookRequest = ItemRequest.builder()
                .name("Book Item")
                .price(20000)
                .stockQuantity(5)
                .category("book")
                .categoryDetail("Author Name")
                .build();

        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                "Test content".getBytes()
        );

        itemService.registerItem(bookRequest, multipartFile);

        Book savedBook = (Book) itemRepository.findAll().get(0);
        assertThat(savedBook.getName()).isEqualTo("Book Item");
        assertThat(savedBook.getPrice()).isEqualTo(20000);
        assertThat(savedBook.getStockQuantity()).isEqualTo(5);
        assertThat(savedBook.getAuthor()).isEqualTo("Author Name");
    }
}
