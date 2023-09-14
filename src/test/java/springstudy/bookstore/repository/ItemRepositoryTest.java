package springstudy.bookstore.repository;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import springstudy.bookstore.controller.api.dto.sort.ItemSearch;
import springstudy.bookstore.domain.dto.item.GetPreViewItemResponse;
import springstudy.bookstore.domain.entity.Address;
import springstudy.bookstore.domain.entity.Item;
import springstudy.bookstore.domain.entity.User;
import springstudy.bookstore.domain.enums.CategoryType;
import springstudy.bookstore.service.UserService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@Slf4j
@SpringBootTest
@Transactional
class ItemRepositoryTest {

    @Autowired ItemRepository itemRepository;
    @Autowired UserService userService;

    public User createUser() {
        Address address = new Address("진주", "10", "1111");
        User userB = new User("test22", "test22!","userT", "nana05555@gmail.com", address);
        return userB;
    }

    public ItemSearch createSearchConditionTest1() {
        ItemSearch itemSearch = new ItemSearch();
        itemSearch.setItemName("Ariana Grande");
        return itemSearch;
    }    public ItemSearch createSearchConditionTest2() {
        ItemSearch itemSearch = new ItemSearch();
        itemSearch.setItemName("Anne");
        return itemSearch;
    }
    @Test
    void ascSort_페이지테스트() {
        // given : 낮은 가격순으로 상품을 조회하려고 한다.
        Pageable pageable = PageRequest.of(0, 4);
        Page<GetPreViewItemResponse> sort = itemRepository.sortByItemPriceASC(pageable);
        List<GetPreViewItemResponse> list = sort.getContent();

        // then : 가장 낮은 가격의 상품이 "Tara Duncan"이 맞는지 확인한다.
        assertThat(list.get(0).getImgName().equals("Tara Duncan"));
    }

    @Test
    void descSort_페이지테스트() {
        // given : 높은 가격순으로 상품조회 하려고 할 때
        Pageable pageable = PageRequest.of(0, 4);
        Page<GetPreViewItemResponse> sort = itemRepository.sortByItemPriceDESC(pageable);

        // then : 가장 높은 가격의 상품이 "BAEK HYUN"이 맞는지 확인한다.
        List<GetPreViewItemResponse> list = sort.getContent();
        assertThat(list.get(0).getImgName().equals("BAEK HYUN"));
    }

    @Test
    void sortByUser_유저별판매제품테스트() {
        // given : "test4" 아이디 회원이 판매하는 상품리스트를 조회하려고 했을 때
        User user = userService.findByLoginId("test4");
        List<Item> itemList = itemRepository.findAllBySellerId(user.getLoginId());

        // then : 총 13개 상품을 판매하는게 맞는지?
        assertThat(itemList.size() == 13);
    }



    @Test
    void sortByCategoryType_카테고리정렬테스트() {
        // given : "BOOK" 카테고리 페이지로 들어갔을 때
        Pageable pageable = PageRequest.of(0, 4);
        String code = CategoryType.BOOK.getCode();
        Page<GetPreViewItemResponse> sort = itemRepository.sortByCategoryType(code, pageable);
        List<GetPreViewItemResponse> content = sort.getContent();

        // then : 해당 상품이 "BOOK"타입이 맞는지?
        assertThat(content.get(0).getCategoryType().getCode().equals(CategoryType.BOOK.getCode()));
        for (GetPreViewItemResponse mainItemDto : content) {
            System.out.println(mainItemDto.toString());
       }
    }

    @Test
    void searchItem_검색테스트() {
        // given : "Ariana Grande"(음반 카테고리 타입 상품이다.)으로 검색했을 때
        Pageable pageable = PageRequest.of(0, 4);
        Page<GetPreViewItemResponse> sort = itemRepository.searchByItemName(createSearchConditionTest1(), pageable);
        List<GetPreViewItemResponse> content = sort.getContent();

        // then : "Ariana Grande" 상품이 "MUSIC" 카테고리 타입이 맞는지?
        String itemName = content.get(0).getItemName();
        Item findItem = itemRepository.findByName(itemName);
        assertThat(findItem.getCategoryType().getCode().equals(CategoryType.MUSIC.getCode()));
    }

    @Test
    void searchItemAndCategoryType_카테고리안에서검색테스트() {
        //given : "BOOK" 카테고리 페이지에서 상품 "Anne"을 검색하려고 한다.
        PageRequest pageable = PageRequest.of(0, 4);
        Page<GetPreViewItemResponse> mainItemDtos = itemRepository.searchByItemNameAndCategoryType(createSearchConditionTest2(), CategoryType.BOOK.getCode(), pageable);

        for (GetPreViewItemResponse mainItemDto : mainItemDtos.getContent()) {
           log.info("mainItemDto.getItemName()", mainItemDto.getItemName());
        }
        // then : 상품 2개가 조회 되는지?
        assertEquals(2, mainItemDtos.getContent().size());

    }


}
