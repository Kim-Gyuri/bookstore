package springstudy.bookstore.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import springstudy.bookstore.domain.enums.CategoryType;
import springstudy.bookstore.domain.enums.IsMainImg;
import springstudy.bookstore.domain.enums.ItemSellStatus;
import springstudy.bookstore.domain.enums.ItemType;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CartTest {

    @Test
    @Transactional
    @DisplayName("장바구니 만들기")
    void 장바구니_생성() {
        Address addressA = new Address("진주", "2", "2222");
        Address addressB = new Address("서울", "1", "1111");

        User userA = new User( "test","test!","userA", "mimi03@naver.com", addressA);
        User userB = new User("test2", "test2!","userB", "nana05@gmail.com", addressB);
        userA.createCart(new Cart());
        userB.createCart(new Cart());

        Item item1 = new Item("spring5", 10000, 10, ItemType.BEST, CategoryType.BOOK, ItemSellStatus.SELL);
        Item item2 = new Item("mvc2", 10000, 10, ItemType.BEST, CategoryType.BOOK, ItemSellStatus.SELL);
        ItemImg img1 = new ItemImg("origin.jpg", "store.jpg", "c:save/store.jpg", IsMainImg.Y, item1);
        ItemImg img2 = new ItemImg("origin.jpg", "store.jpg", "c:save/store.jpg", IsMainImg.Y, item2);

        OrderItem orderItem1 = new OrderItem(userA.getCart(), item1, 5);
        OrderItem orderItem2 = new OrderItem(userA.getCart(), item2, 2);
        userA.addCartItem(orderItem1);
        userA.addCartItem(orderItem2);

        int sum = 0;
        for (OrderItem orderItem : userA.getCart().getOrderItemList()) {
            sum += orderItem.getCount();
        }

        assertThat(sum).isEqualTo(7);
        assertThat(userB.getCart().getOrderItemList().size()).isEqualTo(0);
    }
}