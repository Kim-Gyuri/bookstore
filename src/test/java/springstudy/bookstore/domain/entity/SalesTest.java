package springstudy.bookstore.domain.entity;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import springstudy.bookstore.domain.enums.CategoryType;
import springstudy.bookstore.domain.enums.ItemType;
import springstudy.bookstore.domain.enums.OrderStatus;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Transactional
@SpringBootTest
public class SalesTest {

    @Test
    void uploadSales_정상() {
        // given : "userA", "userB" 회원 2명이 있다.
        Address addressA = new Address("진주", "2", "2222");
        Address addressB = new Address("서울", "1", "1111");

        User userA = new User( "test","test!","userA", "mimi03@naver.com", addressA);
        User userB = new User("test2", "test2!","userB", "nana05@gmail.com", addressB);
        userA.createCart(new Cart());
        userB.createCart(new Cart());
        userB.createSales(new Sales());

        // when :
        // "userB" 회원은 상품을 등록하였고,
        // "userA" 회원이 그 상품을 장바구니에 담았을 때,
        Item item1 = new Item(userB.getLoginId(),"spring5", 10000, 10, ItemType.BEST, CategoryType.BOOK);
        Item item2 = new Item(userB.getLoginId(),"mvc2", 10000, 10, ItemType.BEST, CategoryType.BOOK);

        userB.uploadItem(item1);
        userB.uploadItem(item2);

        OrderItem orderItem1 = new OrderItem(userA.getCart(), item1, 5, LocalDate.now());
        OrderItem orderItem2 = new OrderItem(userA.getCart(), item2, 2, LocalDate.now());

        userA.addCartItem(orderItem1);
        userA.addCartItem(orderItem2);

        userB.getSales().takeOrder(orderItem1);
        userB.getSales().takeOrder(orderItem2);


        // then : "userB" 회원의 판매금액과 "userA" 회원의 결재금액이 같은지?
        int sum = 0;
        for (OrderItem orderItem : userA.getCart().getOrderItemList()) {
            sum += orderItem.getOrderPrice();
        }

        log.info("userA buy item -> orderPrice={}", sum);
        log.info("userB sell item -> total revenue={}", userB.getSales().getTotalRevenue());
        assertThat(userB.getSales().getTotalRevenue()).isEqualTo(sum);

        Sales sales = userB.getSales();
        assertThat(sales.getOrderItemList().size()).isEqualTo(2);
        for (OrderItem orderItem : sales.getOrderItemList()) {
            log.info("userB get order - item info = {}", orderItem.toString());
        }
    }

    @Test
    void uploadSales_주문취소() {
        // given : "userA", "userB" 회원 2명이 있다.
        Address addressA = new Address("진주", "2", "2222");
        Address addressB = new Address("서울", "1", "1111");

        User userA = new User( "test","test!","userA", "mimi03@naver.com", addressA);
        User userB = new User("test2", "test2!","userB", "nana05@gmail.com", addressB);
        userA.createCart(new Cart());
        userB.createCart(new Cart());
        userB.createSales(new Sales());

        // when :
        // "userB" 회원은 상품을 등록하였고,
        // "userA" 회원이 그 상품을 장바구니에 담았을 때,
        Item item1 = new Item(userB.getLoginId(),"spring5", 10000, 10, ItemType.BEST, CategoryType.BOOK);
        Item item2 = new Item(userB.getLoginId(),"mvc2", 10000, 10, ItemType.BEST, CategoryType.BOOK);

        userB.uploadItem(item1);
        userB.uploadItem(item2);

        OrderItem orderItem1 = new OrderItem(userA.getCart(), item1, 5, LocalDate.now());
        OrderItem orderItem2 = new OrderItem(userA.getCart(), item2, 2, LocalDate.now());

        userA.addCartItem(orderItem1);
        userA.addCartItem(orderItem2);

        userB.getSales().takeOrder(orderItem1);
        userB.getSales().takeOrder(orderItem2);

        // then1: "주문" 표시로 바뀌었는지?
        for (OrderItem orderItem : userB.getSales().getOrderItemList()) {
            assertThat(orderItem.getOrderStatus()).isEqualTo(OrderStatus.ORDER);
        }

        // then2 : userA가 orderItem1를 주문취소 했을 때
        userA.getCart().cancelOrderItem(orderItem1);
        userB.getSales().cancelOrder(orderItem1);

        assertThat(orderItem1.getOrderStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(orderItem2.getOrderStatus()).isEqualTo(OrderStatus.ORDER);


    }

}
