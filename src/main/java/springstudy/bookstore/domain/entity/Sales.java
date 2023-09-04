package springstudy.bookstore.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import springstudy.bookstore.domain.enums.OrderStatus;

import javax.persistence.*;
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

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    /*
      비지니스 로직 ;
      uploadItem : 팔고 싶은 상품 등록
      takeOrder : 주문 요청을 받다.
      cancelOrder : 주문 취소요청을 받다.
     */
    public void uploadItem(Item item) {
        itemList.add(item);
        this.orderStatus = OrderStatus.NONE;
    }

    public void takeOrder(int orderPrice) {
        this.orderStatus = OrderStatus.ORDER;
        totalRevenue += orderPrice;
    }

    public void cancelOrder(int orderPrice) {
        totalRevenue -= orderPrice;
        this.orderStatus = OrderStatus.CANCEL;
    }


}
