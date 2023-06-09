package springstudy.bookstore.domain.dto;

import lombok.Builder;
import lombok.Data;
import springstudy.bookstore.domain.entity.Item;

@Data
public class CartInfoDto {
    private Long orderItemId;
    private String loginId;
    private Item item;
    private Integer count;
    private Integer orderPrice;

    @Builder(builderMethodName = "wishItemBuilder")
    public CartInfoDto(Long orderItemId, Item item, Integer count, Integer orderPrice) {
        this.orderItemId = orderItemId;
        this.item = item;
        this.count = count;
        this.orderPrice = orderPrice;
    }


}
