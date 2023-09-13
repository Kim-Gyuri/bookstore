package springstudy.bookstore.domain.dto.cart;

import lombok.Builder;
import lombok.Data;

@Data
public class GetCartResponse {
    private Long orderItemId;
    private String loginId;
    private String itemName;
    private String mainImg_savePath;
    private Integer count;
    private Integer orderPrice;

    @Builder(builderMethodName = "wishItemBuilder")
    public GetCartResponse(Long orderItemId, String loginId, String itemName, String mainImg_savePath, Integer count, Integer orderPrice) {
        this.orderItemId = orderItemId;
        this.loginId = loginId;
        this.itemName = itemName;
        this.mainImg_savePath = mainImg_savePath;
        this.count = count;
        this.orderPrice = orderPrice;
    }


}
