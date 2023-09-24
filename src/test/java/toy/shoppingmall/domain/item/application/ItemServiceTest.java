package toy.shoppingmall.domain.item.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import toy.shoppingmall.domain.item.dao.ItemRepository;
import toy.shoppingmall.domain.item.domain.Book;
import toy.shoppingmall.domain.item.domain.Item;
import toy.shoppingmall.domain.item.dto.ItemRequest;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.domain.Sort.Direction.DESC;


@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired ItemRepository itemRepository;

    @Autowired ItemService itemService;

    @Test
    @DisplayName("상품 등록 테스트")
    void itemRegisterTest() throws IOException {
        /**
         * given : 상품을 1개 등록한다.(registerItem)
         * when : 상품 목록 조회 시
         * then : 요청한 상품 명, 가격, 재고가 예상 값과 같은지 검증한다.
         */

        registerItem();

        Book savedBook = (Book) itemRepository.findAll().get(0);
        assertThat(savedBook.getName()).isEqualTo("Book Item");
        assertThat(savedBook.getPrice()).isEqualTo(20000);
        assertThat(savedBook.getStockQuantity()).isEqualTo(5);
        assertThat(savedBook.getAuthor()).isEqualTo("Author Name");
    }

    @Test
    @DisplayName("상품 페이징 테스트")
    void findItems_PagingTest() throws IOException {
        /**
         * given : 10개의 상품을 등록한다.
         * when : 페이징 정보를 3으로 설정하고 상품 목록을 조회할 때
         * then : 예상한 페이지 당 항목 수, 전체 항목 수, 다음 페이지 존재 여부를 확인한다.
         */

        IntStream.range(0, 10).forEach(i -> {
            ItemRequest itemRequest = ItemRequest.builder()
                    .name("Book Item " + i)
                    .price(20000 + i)
                    .stockQuantity(5)
                    .category("book")
                    .categoryDetail("Author Name " + i)
                    .build();

            MockMultipartFile multipartFile = new MockMultipartFile(
                    "file",
                    "test" + i + ".txt",
                    "text/plain",
                    ("Test content " + i).getBytes()
            );

            try {
                itemService.registerItem(itemRequest, multipartFile);
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        });

        Pageable pageable = PageRequest.of(0, 3, DESC, "id");

        Page<Item> pageableItems = itemService.findItems(pageable);

        assertEquals(3, pageableItems.getContent().size());
        assertEquals(10, pageableItems.getTotalElements());
        assertEquals(4, pageableItems.getTotalPages());
        assertTrue(pageableItems.hasNext());
    }

    @Test
    @DisplayName("상품 수정 테스트")
    void updateItemTest() throws Throwable {
        /**
         * given : 1개의 상품을 등록한다.
         * when : 상품의 이름과 가격을 변경한다(updateRequest)
         * then : 변경 후, 바뀐 이름과 가격이 예상 값과 같은지 검증한다.
         */

        Long itemId = registerItem();

        ItemRequest updateRequest = ItemRequest.builder()
                .name("Book Item2")
                .price(30000)
                .stockQuantity(3)
                .category("book")
                .categoryDetail("Bong")
                .build();

        itemService.updateItem(itemId, updateRequest);

        Item item = itemRepository.getById(itemId);

        assertThat(item.getName()).isEqualTo("Book Item2");
        assertThat(item.getPrice()).isEqualTo(30000);
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    void deleteItemTest() throws Throwable {
        /**
         * given : 1개의 상품을 등록한다.
         * when : 상품의 정보를 삭제한 뒤
         * then : Optional의 값이 비어있다면 성공한다.
         */

        Long itemId = registerItem();

        itemService.deleteItem(itemId);

        Optional<Item> deletedItem = itemRepository.findById(itemId);
        assertThat(deletedItem).isEmpty();
    }

    private Long registerItem() throws IOException {
        ItemRequest request = ItemRequest.builder()
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

        return itemService.registerItem(request, multipartFile);
    }
}
