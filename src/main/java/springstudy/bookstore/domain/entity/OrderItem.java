package springstudy.bookstore.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springstudy.bookstore.domain.dto.cart.GetCartResponse;

import jakarta.persistence.*;
import springstudy.bookstore.domain.enums.OrderStatus;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_id")
    private Sales sales;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private Integer orderPrice;
    private Integer count;
    private LocalDate orderDate;

    // 테스를 위한 데이터 만들기 위해
    // cart()에 담을 때 주문 상품을 생성하기 위해
    @Builder(builderMethodName = "orderItemBuilder")
    public OrderItem(Cart cart, Item item, Integer count, LocalDate orderDate) {
        item.removeStock(count);
        item.checkStatus();
        this.cart = cart;
        this.item = item;
        this.count = count;
        orderAmount(count);
        this.orderDate = orderDate;
    }
    public Long getItemId() {
        return item.getId();
    }

    public void orderRequest() {
        this.orderStatus = OrderStatus.ORDER;

    }

    public void cancelRequest() {
        this.orderStatus = OrderStatus.CANCEL;
    }

    public void orderAmount(Integer count) {
        int sum = item.getPrice() * count;
        this.orderPrice = sum;
    }

    public void update(Item updatedItem) {
        this.item = updatedItem;
        this.orderPrice = updatedItem.getPrice() * this.count;
    }

    public GetCartResponse toWishItemDto() {
        return GetCartResponse.wishItemBuilder()
                .orderItemId(this.id)
                .itemName(item.getName())
                .mainImg_savePath(item.getMainImg_path())
                .count(this.count)
                .orderPrice(this.orderPrice)
                .build();
    }

    @Override
    public String toString() {
        return "OrderItem Info {" + "id=" + id + ", name=" + item.getName() + ",  itemType =" + item.getItemType() + ", orderAmount =" + count + ", orderPrice =" + orderPrice + '}';
    }
}
