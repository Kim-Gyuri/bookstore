package springstudy.bookstore.domain.dto.orderItem;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class GetOrderItemResponse {
    private Long orderItemId;
    private String name;
    private String savePath;
    private Integer count;
    private Integer orderPrice;

    @QueryProjection
    public GetOrderItemResponse(Long orderItemId, String itemName, String savePath, Integer count, Integer orderPrice) {
        this.orderItemId = orderItemId;
        this.name = itemName;
        this.savePath = savePath;
        this.count = count;
        this.orderPrice = orderPrice;
    }
}
