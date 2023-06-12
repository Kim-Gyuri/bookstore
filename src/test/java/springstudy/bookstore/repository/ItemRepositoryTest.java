package springstudy.bookstore.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import springstudy.bookstore.domain.dto.MainItemDto;
import springstudy.bookstore.domain.dto.sort.ItemSearchCondition;
import springstudy.bookstore.domain.entity.Address;
import springstudy.bookstore.domain.entity.Item;
import springstudy.bookstore.domain.entity.User;
import springstudy.bookstore.domain.enums.CategoryType;
import springstudy.bookstore.service.UserService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    public ItemSearchCondition createCondition() {
        return ItemSearchCondition.builder()
                .itemName("Ariana Grande")
                .loginMember(createUser())
                .build();
    }
    
    @Test
    void ascSort_페이지테스트() {
        // given : 낮은 가격순으로 상품을 조회하려고 한다.
        Pageable pageable = PageRequest.of(0, 4);
        Page<MainItemDto> sort = itemRepository.sortByItemPriceASC(pageable);
        List<MainItemDto> list = sort.getContent();

        // then : 가장 낮은 가격의 상품이 "Tara Duncan"이 맞는지 확인한다.
        assertThat(list.get(0).getImgName().equals("Tara Duncan"));
        for (MainItemDto mainItemDto : list) {
            System.out.println(mainItemDto.toString());
        }
    }

    @Test
    void descSort_페이지테스트() {
        // given : 높은 가격순으로 상품조회 하려고 할 때
        Pageable pageable = PageRequest.of(0, 4);
        Page<MainItemDto> sort = itemRepository.sortByItemPriceDESC(pageable);

        // then : 가장 높은 가격의 상품이 "BAEK HYUN"이 맞는지 확인한다.
        List<MainItemDto> list = sort.getContent();
        assertThat(list.get(0).getImgName().equals("BAEK HYUN"));
        for (MainItemDto mainItemDto : list) {
            System.out.println(mainItemDto.toString());
        }
    }

    @Test
    void sortByUser_유저별판매제품테스트() {
        // given : "test4" 아이디 회원이 판매하는 상품리스트를 조회하려고 했을 때
        User user = userService.findOne("test4");
        List<Item> itemList = itemRepository.findAllByUser(user);

        // then : 총 13개 상품을 판매하는게 맞는지?
        assertThat(itemList.size() == 13);
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    void sortByCategoryType_카테고리정렬테스트() {
        // given : "BOOK" 카테고리 페이지로 들어갔을 때
        Pageable pageable = PageRequest.of(0, 4);
        String code = CategoryType.BOOK.getTypeCode();
        Page<MainItemDto> sort = itemRepository.sortByCategoryType(code, pageable);
        List<MainItemDto> content = sort.getContent();

        // then : 해당 상품이 "BOOK"타입이 맞는지?
        assertThat(content.get(0).getCategoryType().getTypeCode().equals(CategoryType.BOOK.getTypeCode()));
        for (MainItemDto mainItemDto : content) {
            System.out.println(mainItemDto.toString());
       }
    }

    @Test
    void searchItem_검색테스트() {
        // given : "Ariana Grande"(음반 카테고리 타입 상품이다.)으로 검색했을 때
        Pageable pageable = PageRequest.of(0, 4);
        ItemSearchCondition condition = createCondition();
        Page<MainItemDto> sort = itemRepository.searchByItemName(condition, pageable);
        List<MainItemDto> content = sort.getContent();

        for (MainItemDto mainItemDto : content) {
            System.out.println(mainItemDto.toString());
        }

        // then : "Ariana Grande" 상품이 "MUSIC" 카테고리 타입이 맞는지?
        String itemName = content.get(0).getItemName();
        Item findItem = itemRepository.findByItemName(itemName);
        assertThat(findItem.getCategoryType().getTypeCode().equals(CategoryType.MUSIC.getTypeCode()));
        System.out.println(findItem.toString());
    }
    
    @Test
    void searchItemAndCategoryType_카테고리안에서검색테스트() {
        //given : "BOOK" 카테고리 페이지에서 상품 "Anne"을 검색하려고 한다.
        PageRequest pageable = PageRequest.of(0, 4);
        ItemSearchCondition condition = createCondition();
        Page<MainItemDto> mainItemDtos = itemRepository.searchByItemNameAndCategoryType(condition, CategoryType.BOOK.getTypeCode(), pageable);


        // then : 상품 2개가 조회 되는지?
        assertEquals(2, mainItemDtos.getContent().size());
        for (MainItemDto mainItemDto : mainItemDtos) {
            System.out.println("mainItemDto.toString() = " + mainItemDto.toString());
        }

    }


}
