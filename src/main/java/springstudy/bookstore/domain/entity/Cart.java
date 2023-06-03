package springstudy.bookstore.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {

    @Id @GeneratedValue
    @Column(name = "cart_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<OrderItem> orderItemList = new ArrayList<>();

    public static Cart createCart(User user, OrderItem... orderItems) {
        Cart cart = new Cart();
        cart.setUpUser(user);
        for (OrderItem orderItem : orderItems) {
            cart.addOrderItem(orderItem);
        }
        return cart;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItemList.add(orderItem);
        orderItem.addCart(this);
    }

    public void setUpUser(User user) {
        this.user = user;
    }


}
