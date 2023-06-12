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
import springstudy.bookstore.util.exception.DuplicateOrderItemException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        // given : 회원 Customer 주어졌을 때 장바구니에 상품을 10개 담으려고 한다.
        Long userId = createCustomerTest();
        User customer = userService.findById(userId);
        Item item = createItemTest();
        int orderCount = 10;

        // when : 상품 담기 로직 실행, 10개를 주문한다.
        cartService.addWishList(customer.getLoginId(), item.getId(), 10);

        //then : 남은 재고가 90개가 맞는지?
        List<CartInfoDto> cartList = cartService.getWishList(customer.getLoginId());
        for (CartInfoDto cartInfoDto : cartList) {
            assertEquals(cartInfoDto.getItem(), item);
            assertEquals(cartInfoDto.getCount(), orderCount);
            assertEquals(90, item.getStockQuantity());
        }
    }

    @Test
    @DisplayName("중복된 상품이 아닌 경우 장바구니에 상품을 추가한다.")
    public void addWishList() {
        // given : "test" (아이디 사용자)가 "Ariana Grande" (상품)을 이미 장바구니에 담았었다.
        String testUserId = "test";
        String testOrderItemName = "Ariana Grande";
        User user = userService.findOne(testUserId);
        Item item = itemRepository.findByItemName(testOrderItemName);

        // when : 다시 "Ariana Grande" 를 2개 담으려고 했을 때
        // 중복된 상품을 담았을 때 예외가 발생하는지?
        Throwable e  = assertThrows(DuplicateOrderItemException.class, () -> cartService.addWishList(user.getLoginId(), item.getId(), 2));
        assertEquals("중복된 장바구니입니다.", e.getMessage());
    }
}