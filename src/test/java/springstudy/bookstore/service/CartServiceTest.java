package springstudy.bookstore.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import springstudy.bookstore.domain.dto.ItemFormDto;
import springstudy.bookstore.domain.dto.UserFormDto;
import springstudy.bookstore.domain.entity.Item;
import springstudy.bookstore.domain.entity.User;
import springstudy.bookstore.domain.enums.CategoryType;
import springstudy.bookstore.domain.enums.ItemSellStatus;
import springstudy.bookstore.domain.enums.ItemType;
import springstudy.bookstore.repository.ItemRepository;
import springstudy.bookstore.util.exception.DuplicateOrderItemException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
@Slf4j
@SpringBootTest
@Transactional
class CartServiceTest {

    @Autowired CartService cartService;
    @Autowired ItemRepository itemRepository;
    @Autowired UserService userService;

    public User createBuyerTest() {
        UserFormDto dto = new UserFormDto();
        dto.setLoginId("wolf27");
        dto.setPassword("1234@");
        dto.setName("wolf");
        dto.setEmail("wolf1004@naver.com");
        dto.setCity("서울");
        dto.setStreet("마포구 합정동");
        dto.setZipcode("4015");

        userService.signUp(dto);
        return userService.findByLoginId(dto.getLoginId());
    }

    public User createSellerTest() {
        UserFormDto dto = new UserFormDto();
        dto.setLoginId("nana20");
        dto.setPassword("1234@");
        dto.setName("faker");
        dto.setEmail("karis99@naver.com");
        dto.setCity("서울");
        dto.setStreet("마포구 합정동");
        dto.setZipcode("1120");

        userService.signUp(dto);
        return userService.findOne(dto.getLoginId());
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
        User buyer = createBuyerTest(); // 구매자
        User seller = createSellerTest(); // 판매자

        Item item = createItemTest();
        seller.uploadItem(item);    // 판매자가 상품 등록

        // 구매자는 10개를 주문하려고 한다.
        int orderCount = 10;

        // when : 상품 담기 로직 실행, 10개를 주문한다.
        cartService.addWishList(buyer.getLoginId(), item.getId(), 10);

        //then
        // 판매자가 파는 상품의 남은 재고가 90개가 맞는지?
        assertThat(90).isEqualTo(seller.getSales().getItemList().get(0).getStockQuantity());

        // 판매자 판매액과 구매자 결재금액은 같은지?
        assertThat(seller.getSales().getTotalRevenue()).isEqualTo(buyer.getCart().getOrderItemList().get(0).getOrderPrice());
        log.info("buyer -> order price ={}", buyer.getCart().getOrderItemList().get(0).getOrderPrice());
        log.info("seller -> total revenue ={}",  seller.getSales().getTotalRevenue());

    }

    @Test
    @DisplayName("중복된 상품을 담을 경우, 예외가 터진다.")
    public void addWishList() {
        // given : 회원 Customer 주어졌을 때 장바구니에 상품을 10개 담으려고 한다.
        User buyer = createBuyerTest(); // 구매자
        User seller = createSellerTest(); // 판매자

        Item item = createItemTest();
        seller.uploadItem(item);    // 판매자가 상품 등록


        // given : 구매자 장바구니에 (상품)을 이미 장바구니에 담았었다.
        cartService.addWishList(buyer.getLoginId(), item.getId(), 10);

        // when : 다시 2개 담으려고 했을 때
        // 중복된 상품을 담았을 때 예외가 발생하는지?
        Throwable e  = assertThrows(DuplicateOrderItemException.class, () -> cartService.addWishList(buyer.getLoginId(), item.getId(), 2));
        assertEquals("중복된 장바구니입니다.", e.getMessage());
    }
}