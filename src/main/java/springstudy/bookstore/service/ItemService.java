package springstudy.bookstore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import springstudy.bookstore.controller.dto.ItemSortParam;
import springstudy.bookstore.domain.dto.item.*;
import springstudy.bookstore.domain.dto.itemImg.CreateImgRequest;
import springstudy.bookstore.domain.dto.sort.ItemSearchCondition;
import springstudy.bookstore.domain.entity.Item;
import springstudy.bookstore.domain.entity.ItemImg;
import springstudy.bookstore.domain.entity.User;
import springstudy.bookstore.domain.enums.CategoryType;
import springstudy.bookstore.domain.enums.IsMainImg;
import springstudy.bookstore.repository.ItemRepository;
import springstudy.bookstore.util.exception.item.DuplicateItemException;
import springstudy.bookstore.util.exception.item.NotFoundImgFileException;
import springstudy.bookstore.util.exception.item.NotFoundItemException;

import java.io.IOException;
import java.util.List;

import static springstudy.bookstore.util.constant.Constants.FIRST_FILE;
import static springstudy.bookstore.util.constant.Constants.THUMBNAIL_INDEX;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;

    // 상품 등록
    public Long saveItem(User user, CreateItemRequest dto, List<MultipartFile> multipartFileList) throws IOException {
        validateDuplicateItem(dto);

        Item item = dto.toEntity(user.getLoginId());
        log.info("잘 저장되었는지?={}", item.toString());
        Long id = itemRepository.save(item).getId(); // 상품 상세 정보 저장

        validateImgFiles(multipartFileList); // 필수로 이미지 1개를 업로드 했는지?

       getThumbnailImage(multipartFileList, item);   // 상품 이미지 저장 (# pick thumbnail)

        user.uploadItem(item); // 판매자 상품 업로드
        return id;  // 상품 id 반환
    }

    private static void validateImgFiles( List<MultipartFile> multipartFileList) throws NotFoundImgFileException {
        if (multipartFileList.get(FIRST_FILE).isEmpty()) {
            throw new NotFoundImgFileException("첫번째 상품 이미지는 필수 입력 값 입니다.");
        }
    }

    private void getThumbnailImage(List<MultipartFile> multipartFileList, Item item) throws IOException {

        for (int i=0; i< multipartFileList.size(); i++) {
            CreateImgRequest itemInfo = new CreateImgRequest(item);

            if (i== THUMBNAIL_INDEX)
                itemInfo.setYN(IsMainImg.Y);
            else
                itemInfo.setYN(IsMainImg.N);

           itemImgService.saveItemImg(itemInfo, multipartFileList.get(i));
        }
    }

    // 중복된 상품 등록인지 확인하는 로직 ; 상품 이름, 상품 카테고리가 동일한 상품 등록인지?
    @Transactional(readOnly = true)
    public void validateDuplicateItem(CreateItemRequest dto) {
        if (itemRepository.existsByItemNameAndCategoryType(dto.getName(), CategoryType.valueOf(dto.getCategoryType()))) {
            throw new DuplicateItemException("이미 등록된 상품이 존재합니다.");
        }
    }

    @Transactional(readOnly = true)
    public Item findById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundItemException("해당 " + id + " 번호 상품이 없습니다."));
    }

    @Transactional(readOnly = true)
    public GetDetailItemResponse getItemDetail(Long itemId) {
        Item item = findById(itemId);
        return new GetDetailItemResponse(item);
    }

    @Transactional(readOnly = true)
    public Page<GetPreViewItemResponse> searchPageSort(ItemSearchCondition condition, Pageable pageable) {
        return itemRepository.searchByItemName(condition, pageable);
    }

    @Transactional(readOnly = true)
    public Page<GetPreViewItemResponse> categoryPageSort(String code, Pageable pageable) {
        return itemRepository.sortByCategoryType(code, pageable);
    }

    @Transactional(readOnly = true)
    public Page<GetPreViewItemResponse> itemPriceSort(String code, Pageable pageable) {
        if (code.equals(ItemSortParam.DESC.getCode())) {
            return itemRepository.sortByItemPriceDESC(pageable);
        } else {
            return itemRepository.sortByItemPriceASC(pageable);
        }
    }

    @Transactional(readOnly = true)
    public Page<GetPreViewItemResponse> searchAndCategory(ItemSearchCondition condition, String code, Pageable pageable) {
        return itemRepository.searchByItemNameAndCategoryType(condition, code, pageable);
    }

    public void delete(Long id) {
        Item item = findById(id);
        itemRepository.delete(item);
    }

    public void deleteImg(Long itemId, Long imgId) {
        ItemImg imgEntity = itemImgService.findByImgId(imgId);
        Item item = findById(itemId);

        itemImgService.delete(imgEntity);
        item.deleteItemImg(imgEntity);
    }

    public void update(Long itemId, UpdateItemRequest form, List<MultipartFile> multipartFileList) throws IOException {

        Item findItem = findById(itemId); // 상품 엔티티를 꺼내고
        validateImg(findItem); // 상품 엔티티에 이미지 파일이 존재하는지 확인한다. (대표 이미지 1개라도 있어야 한다.)
                                // -> "delete icon" 으로 지울 수도 있으니까

        // 상품 정보 수정 (이름/가격/수량 수정가능하다.)
        if (form != null) {
            findItem.update(form.getItemName(), form.getPrice(), form.getQuantity());
        }

        // 이미지를 추가로 넣으려고 한다.
        if (!multipartFileList.isEmpty()) {
            for (MultipartFile file : multipartFileList) {
                Long imgId = itemImgService.saveItemImg(new CreateImgRequest(findItem), file); // 이미지 엔티티를 만들고
                findItem.addMoreItemImg(itemImgService.findByImgId(imgId)); //
            }
        }

        // 로그 확인차
        for (ItemImg img : findItem.getImgList()) {
            log.info("추가로 등록한 이미지 파일 정보={}", img.getSavePath());
        }

    }

    // 상품 이미지가 1개라도 있는지?
    private static void validateImg(Item item) throws NotFoundImgFileException {
        if (item.getImgList().isEmpty()) {
            throw new NotFoundImgFileException("상품 이미지를 넣어주세요.");
        }
    }

}
