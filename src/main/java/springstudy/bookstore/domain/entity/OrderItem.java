package springstudy.bookstore.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private Cart cart;

    private Integer orderPrice;
    private Integer count;

    @Builder(builderMethodName = "orderItemBuilder")
    public OrderItem(Item item, Integer count) {
        item.removeStock(count);
        item.checkStatus();
        this.item = item;
        this.count = count;
    }

    public void addCart(Cart cart) {
        this.cart = cart;
    }

    public void orderAmount(Integer count) {
        int sum = item.getPrice() * count;
        this.orderPrice = sum;
    }
}
