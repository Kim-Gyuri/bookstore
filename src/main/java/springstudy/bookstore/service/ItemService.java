package springstudy.bookstore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import springstudy.bookstore.controller.dto.ItemSortParam;
import springstudy.bookstore.domain.dto.*;
import springstudy.bookstore.domain.dto.sort.ItemSearchCondition;
import springstudy.bookstore.domain.entity.Item;
import springstudy.bookstore.domain.entity.ItemImg;
import springstudy.bookstore.domain.entity.User;
import springstudy.bookstore.domain.enums.IsMainImg;
import springstudy.bookstore.repository.ItemImgRepository;
import springstudy.bookstore.repository.ItemRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;

    public Long saveItem_test(ItemFormDto itemFormDto, List<MultipartFile> multipartFileList) throws IOException {
        Item item = itemFormDto.toEntity(); // 아이템 정보
        getThumbnailImage(multipartFileList, item); // 아이템 이미지 첨부
        Long id = itemRepository.save(item).getId(); // 저장

        return id;
    }
    public Long saveItem(User user, ItemFormDto itemFormDto, List<MultipartFile> multipartFileList) throws IOException {
        Item item = itemFormDto.toEntity();
        item.setUpUser(user);
        Long id = itemRepository.save(item).getId();

        // 대표 이미지 구별
       getThumbnailImage(multipartFileList, item);
        return id;
    }
    private void getThumbnailImage(List<MultipartFile> multipartFileList, Item item) throws IOException {
        // 대표 이미지 구별
        for (int i = 0; i< multipartFileList.size(); i++) {
            ItemInfoDto itemInfo = new ItemInfoDto();
            itemInfo.setItem(item);

            if (i==0)
                itemInfo.setYN(IsMainImg.Y);
            else
                itemInfo.setYN(IsMainImg.N);

           itemImgService.saveItemImg(itemInfo, multipartFileList.get(i));
        }
    }
    /*
    public Long saveItem_s3(User user, ItemFormDto itemFormDto, List<MultipartFile> multipartFileList) throws IOException {
        Item item = itemFormDto.toEntity();
        item.setUpUser(user);
        Long id = itemRepository.save(item).getId();

        // 대표 이미지 구별
        for (int i=0; i<multipartFileList.size(); i++) {
            ItemInfoDto itemInfo = new ItemInfoDto();
            itemInfo.setItem(item);

            if (i==0)
                itemInfo.setYN(IsMainImg.Y);
            else
                itemInfo.setYN(IsMainImg.N);


            itemImgService.saveItemImg_s3(itemInfo, multipartFileList.get(i));
        }
        return id;
    }
*/


    @Transactional(readOnly = true)
    public Item findById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 " + id + " 번호 상품이 없습니다."));
    }

    @Transactional(readOnly = true)
    public ItemFormDto getItemDetail(Long itemId) {
        List<ItemImg> itemImgList = itemImgRepository.findAllByItem_id(itemId);
        List<ItemImgDto> itemImgDtoList = new ArrayList<>();

        for (ItemImg itemImg : itemImgList) {
            ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
            itemImgDtoList.add(itemImgDto);
        }

        //item(Entity) -> itemformDto(DTO) --> itemimgdtoList (setting)
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 " + itemId + " 번호 상품이 없습니다."));

        ItemFormDto itemFormDto = ItemFormDto.of(item);
        itemFormDto.setItemImgDtoList(itemImgDtoList);
      //  itemFormDto.setItemImgDtoList(itemImgDtoList);
        return itemFormDto;
    }

    @Transactional(readOnly = true)
    public SalesFormDto getItemDetail_test(Long itemId) {
        Item item = findById(itemId);
        return new SalesFormDto(item);
    }

    @Transactional(readOnly = true)
    public Page<MainItemDto> searchPageSort(ItemSearchCondition condition, Pageable pageable) {
        return itemRepository.searchByItemName(condition, pageable);
    }

    @Transactional(readOnly = true)
    public Page<MainItemDto> categoryPageSort(String code, Pageable pageable) {
        return itemRepository.sortByCategoryType(code, pageable);
    }

    @Transactional(readOnly = true)
    public Page<MainItemDto> itemPriceSort(String code, Pageable pageable) {
        if (code.equals(ItemSortParam.DESC.getCode())) {
            return itemRepository.sortByItemPriceDESC(pageable);
        } else {
            return itemRepository.sortByItemPriceASC(pageable);
        }
    }

    @Transactional(readOnly = true)
    public Page<MainItemDto> searchAndCategory(ItemSearchCondition condition, String code, Pageable pageable) {
        return itemRepository.searchByItemNameAndCategoryType(condition, code, pageable);
    }

    public void delete(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다."));
        itemRepository.delete(item);
    }


    public void update(Long itemId, ItemUpdateForm form, List<MultipartFile> multipartFileList) throws IOException {
        List<ItemImg> imgTemp = new ArrayList<>();

        Item findItem = findById(itemId);
        findItem.update(form.getItemName(), form.getPrice(), form.getQuantity());

        // 대표 이미지 구별
        for (int i=0; i<multipartFileList.size(); i++) {
            ItemInfoDto itemInfo = new ItemInfoDto();
            itemInfo.setItem(findItem);

            if (i==0)
                itemInfo.setYN(IsMainImg.Y);
            else
                itemInfo.setYN(IsMainImg.N);

            Long imgId = itemImgService.saveItemImg(itemInfo, multipartFileList.get(i));
            ItemImg imgEntity = itemImgService.findByImgId(imgId);
            imgTemp.add(imgEntity);
        }

        // 이미지 추가하기
        for (ItemImg img : imgTemp) {
            findItem.addMoreItemImg(img);
        }
    }
}
