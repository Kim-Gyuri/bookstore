package springstudy.bookstore.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import springstudy.bookstore.domain.dto.CartInfoDto;
import springstudy.bookstore.domain.dto.ItemFormDto;
import springstudy.bookstore.domain.dto.UserFormDto;
import springstudy.bookstore.domain.entity.Item;
import springstudy.bookstore.domain.entity.User;
import springstudy.bookstore.domain.enums.CategoryType;
import springstudy.bookstore.domain.enums.ItemSellStatus;
import springstudy.bookstore.domain.enums.ItemType;
import springstudy.bookstore.repository.ItemRepository;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CartServiceTest {

    @Autowired CartService cartService;
    @Autowired ItemRepository itemRepository;
    @Autowired UserService userService;

    public Long createCustomerTest() {
        UserFormDto dto = new UserFormDto();
        dto.setLoginId("wolf27");
        dto.setPassword("1234@");
        dto.setName("wolf");
        dto.setEmail("wolf1004@naver.com");
        dto.setCity("서울");
        dto.setStreet("마포구 합정동");
        dto.setZipcode("4015");

        return userService.signUp(dto);
    }

    public Item createItemTest() {

        ItemFormDto dto = new ItemFormDto();
        dto.setItemName("테스트 상품명");
        dto.setCategoryType(CategoryType.BOOK);
        dto.setItemType(ItemType.BEST);
        dto.setPrice(10000);
        dto.setQuantity(100);
        dto.setStatus(ItemSellStatus.SELL);

        return itemRepository.save(dto.toEntity());
    }

    @Test
    @DisplayName("장바구니 담기 테스트")
    void addCart() {

        Long userId = createCustomerTest();
        User customer = userService.findById(userId);
        Item item = createItemTest();
        int orderCount = 10;

        cartService.mergeCart(customer, item.getId(), 10);

        List<CartInfoDto> cartList = cartService.getCartList(customer);
        for (CartInfoDto cartInfoDto : cartList) {
            assertEquals(cartInfoDto.getItem(), item);
            assertEquals(cartInfoDto.getCount(), orderCount);
            assertEquals(90, item.getStockQuantity());
        }
    }

}