package springstudy.bookstore.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springstudy.bookstore.domain.dto.CartInfoDto;

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

    public CartInfoDto toWishItemDto() {
        return CartInfoDto.wishItemBuilder()
                .orderItemId(this.id)
                .item(item)
                .count(this.count)
                .orderPrice(this.orderPrice)
                .build();
    }

}
