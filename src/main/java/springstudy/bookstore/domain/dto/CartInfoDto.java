package springstudy.bookstore.domain.dto;

import lombok.Data;
import springstudy.bookstore.domain.entity.Item;

@Data
public class CartInfoDto {
    private Long orderItemId;
    private String loginId;
    private Item item;
    private Integer count;
    private Integer orderPrice;

    public void updateCartInfo(Long orderItemId, String loginId, Item item, Integer count, Integer orderPrice) {
        this.orderItemId = orderItemId;
        this.loginId = loginId;
        this.item = item;
        this.count = count;
        this.orderPrice = orderPrice;
    }


}
