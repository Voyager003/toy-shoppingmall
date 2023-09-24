package toy.shoppingmall.domain.item.application;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import toy.shoppingmall.domain.item.dao.ItemRepository;
import toy.shoppingmall.domain.item.domain.Album;
import toy.shoppingmall.domain.item.domain.Book;
import toy.shoppingmall.domain.item.domain.Item;
import toy.shoppingmall.domain.item.domain.Movie;
import toy.shoppingmall.domain.item.dto.ItemRequest;
import toy.shoppingmall.domain.item.dto.ItemResponse;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    @Value("${upload.path}")
    private String uploadPath;

    private final ItemRepository itemRepository;

    /* 상품 정보 등록 */
    @Transactional
    public Long registerItem(ItemRequest request, MultipartFile imgFile) throws IOException {
        Item newItem = createItem(request, imgFile);
        if (newItem != null) {
            itemRepository.save(newItem);
        }
        return newItem.getId();
    }

    private Item createItem(ItemRequest request, MultipartFile imgFile) throws IOException {
        String category = request.getCategory();
        String categoryDetail = request.getCategoryDetail();
        String imagePath = generateImageUrl(imgFile);

        switch (category) {
            case "book":
                return Book.builder()
                        .name(request.getName())
                        .price(request.getPrice())
                        .stockQuantity(request.getStockQuantity())
                        .author(categoryDetail)
                        .imagePath(imagePath)
                        .build();
            case "album":
                return Album.builder()
                        .name(request.getName())
                        .price(request.getPrice())
                        .stockQuantity(request.getStockQuantity())
                        .artist(categoryDetail)
                        .imagePath(imagePath)
                        .build();
            case "movie":
                return Movie.builder()
                        .name(request.getName())
                        .price(request.getPrice())
                        .stockQuantity(request.getStockQuantity())
                        .director(categoryDetail)
                        .imagePath(imagePath)
                        .build();
            default:
                return null;
        }
    }

    private String generateImageUrl(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        String fileName = generateUniqueFileName(file.getOriginalFilename());
        File targetFile = new File(uploadPath, fileName);
        file.transferTo(targetFile);
        return fileName;
    }

    private String generateUniqueFileName(String originalFilename) {
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        return System.currentTimeMillis() + "_" + UUID.randomUUID().toString() + extension;
    }

    /* 상품 단건 조회 */
    public ItemResponse findItem(Long itemId) throws Throwable {
        Item item = itemRepository.getById(itemId);
        return new ItemResponse(item);
    }

    /* 페이징 조회 */
    public Page<Item> findItems(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }


    /* 상품 정보 수정 */
    @Transactional
    public Long updateItem(Long itemId, ItemRequest request) throws Throwable {
        Item id = itemRepository.getById(itemId);
        id.updateItemInfo(request.getName(), request.getPrice());
        return itemId;
    }

    /* 상품 정보 삭제 */
    @Transactional
    public void deleteItem(Long itemId) throws Throwable {
        Item item = itemRepository.getById(itemId);
        itemRepository.delete(item);
    }
}
