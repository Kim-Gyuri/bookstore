package springstudy.bookstore.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springstudy.bookstore.domain.dto.cart.GetCartResponse;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    private Integer orderPrice;
    private Integer count;

    // 테스를 위한 데이터 만들기 위해
    // cart()에 담을 때 주문 상품을 생성하기 위해
    @Builder(builderMethodName = "orderItemBuilder")
    public OrderItem(Cart cart, Item item, Integer count) {
        item.removeStock(count);
        item.checkStatus();
        this.cart = cart;
        this.item = item;
        this.count = count;
        orderAmount(count);
    }
    public Long getItemId() {
        return item.getId();
    }
    public void orderAmount(Integer count) {
        int sum = item.getPrice() * count;
        this.orderPrice = sum;
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

}
