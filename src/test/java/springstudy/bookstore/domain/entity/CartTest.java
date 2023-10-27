package springstudy.bookstore.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import springstudy.bookstore.domain.enums.CategoryType;
import springstudy.bookstore.domain.enums.ItemType;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CartTest {

    @Test
    @Transactional
    @DisplayName("장바구니 만들기")
    void 장바구니_생성() {
        // given : "userA", "userB" 회원 2명이 있다.
        Address addressA = new Address("진주", "2", "2222");
        Address addressB = new Address("서울", "1", "1111");

        User userA = new User( "test","test!","userA", "mimi03@naver.com", addressA);
        User userB = new User("test2", "test2!","userB", "nana05@gmail.com", addressB);
        userA.createCart(new Cart());
        userB.createCart(new Cart());

        Item item1 = new Item(userB.getLoginId(), "spring5", 10000, 10, ItemType.BEST, CategoryType.BOOK);
        Item item2 = new Item(userB.getLoginId(),"mvc2", 10000, 10, ItemType.BEST, CategoryType.BOOK);

        userB.uploadItem(item1);
        userB.uploadItem(item2);

        OrderItem orderItem1 = new OrderItem(userA.getCart(), item1, 5, LocalDate.now());
        OrderItem orderItem2 = new OrderItem(userA.getCart(), item2, 2, LocalDate.now());

        // when :
        // "userA" 회원이 상품을 장바구니에 담았을 때,
        // "userB" 회원은 장바구니에 아무것도 담지 않았다.
        userA.addCartItem(orderItem1);
        userA.addCartItem(orderItem2);

        int sum = 0;
        for (OrderItem orderItem : userA.getCart().getOrderItemList()) {
            sum += orderItem.getCount();
        }

        // then :
        // "userA"가 담은 상품 개수가 총 7개가 맞는지?
        //  "userB"는 장바구니에 상품을 담지 않았는지?
        assertThat(sum).isEqualTo(7);
        assertThat(userB.getCart().getOrderItemList().size()).isEqualTo(0);
    }
}