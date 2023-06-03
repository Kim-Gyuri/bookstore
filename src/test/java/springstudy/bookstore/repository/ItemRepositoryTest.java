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
import springstudy.bookstore.domain.enums.ItemType;
import springstudy.bookstore.service.UserService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
        ItemSearchCondition dto = new ItemSearchCondition();
        dto.setItemName("Ariana Grande");
        dto.setLoginMember(createUser());

        return dto;
    }
    
    @Test
    void ascSort_페이지테스트() {
        Pageable pageable = PageRequest.of(0, 4);
        Page<MainItemDto> sort = itemRepository.sortByItemPriceASC(pageable);

        List<MainItemDto> list = sort.getContent();
        assertThat(list.get(0).getImgName().equals("Tara Duncan"));

        for (MainItemDto mainItemDto : list) {
            System.out.println(mainItemDto.toString());
        }
    }

    @Test
    void descSort_페이지테스트() {
        Pageable pageable = PageRequest.of(0, 4);
        Page<MainItemDto> sort = itemRepository.sortByItemPriceDESC(pageable);

        List<MainItemDto> list = sort.getContent();
        assertThat(list.get(0).getImgName().equals("BAEK HYUN"));

        for (MainItemDto mainItemDto : list) {
            System.out.println(mainItemDto.toString());
        }
    }

    @Test
    void sortByUser_유저별판매제품테스트() {
        User user = userService.findOne("test4");
        List<Item> itemList = itemRepository.findAllByUser(user);
        assertThat(itemList.size() == 13);

        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    void sortByCategoryType_카테고리정렬테스트() {
        CategoryType book = CategoryType.BOOK;
        book.getTypeCode();
        book.getCategoryName();
        ItemType best = ItemType.BEST;
        best.getCode();
        best.getItemType();

        Pageable pageable = PageRequest.of(0, 4);
        ItemSearchCondition condition = createCondition();
        String code = CategoryType.BOOK.getTypeCode();;
        Page<MainItemDto> sort = itemRepository.sortByCategoryType(code, pageable);
        List<MainItemDto> content = sort.getContent();

        assertThat(content.get(0).getCategoryType().getTypeCode().equals(CategoryType.BOOK.getTypeCode()));
        for (MainItemDto mainItemDto : content) {
            System.out.println(mainItemDto.toString());
       }
    }

    @Test
    void searchItem_검색테스트() {
        Pageable pageable = PageRequest.of(0, 4);
        ItemSearchCondition condition = createCondition();
        Page<MainItemDto> sort = itemRepository.searchByItemName(condition, pageable);
        List<MainItemDto> content = sort.getContent();

        for (MainItemDto mainItemDto : content) {
            System.out.println(mainItemDto.toString());
        }

        String itemName = content.get(0).getItemName();
        Item findItem = itemRepository.findByItemName(itemName);
        assertThat(findItem.getCategoryType().getTypeCode().equals(CategoryType.MUSIC.getTypeCode()));
        System.out.println(findItem.toString());
    }
    
    @Test
    void searchItemAndCategoryType_카테고리안에서검색테스트() {
        PageRequest pageable = PageRequest.of(0, 4);
        ItemSearchCondition condition = createCondition();
        Page<MainItemDto> mainItemDtos = itemRepository.searchByItemNameAndSortByItemPrice(condition, CategoryType.BOOK.getTypeCode(), pageable);
        assertThat(mainItemDtos.getSize() == 0);

        for (MainItemDto mainItemDto : mainItemDtos) {
            System.out.println("mainItemDto.toString() = " + mainItemDto.toString());
        }

    }


}
