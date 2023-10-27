package springstudy.bookstore.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Sales {

    @Id @GeneratedValue
    @Column(name = "sales_id")
    private Long id;

    private int totalRevenue;

    @OneToMany(mappedBy = "sales", cascade = CascadeType.ALL)
    private List<Item> itemList = new ArrayList<>();

    @OneToMany(mappedBy = "sales", cascade = CascadeType.ALL)
    private List<OrderItem> orderItemList = new ArrayList<>();


    /*
      비지니스 로직 ;
      uploadItem : 팔고 싶은 상품 등록
      takeOrder : 주문 요청을 받다.
      cancelOrder : 주문 취소요청을 받다.
     */
    public void uploadItem(Item item) {
        itemList.add(item);
    }

    public void takeOrder(OrderItem orderItem) {
        orderItem.orderRequest();
        orderItemList.add(orderItem);
        totalRevenue += orderItem.getOrderPrice();
    }

    public void cancelOrder(OrderItem orderItem) {
        orderItem.cancelRequest();
        orderItemList.remove(orderItem);
        totalRevenue -= orderItem.getOrderPrice();
    }


}
