package springstudy.bookstore.domain.dto.orderItem;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import springstudy.bookstore.domain.enums.OrderStatus;

@Data
public class GetOrderItemResponse {
    private Long orderItemId;
    private Long itemId;
    private String name;
    private String savePath;
    private Integer count;
    private Integer orderPrice;
    private OrderStatus orderStatus;

    @QueryProjection
    public GetOrderItemResponse(Long orderItemId, Long itemId, String itemName, String savePath, Integer count, Integer orderPrice, OrderStatus orderStatus) {
        this.orderItemId = orderItemId;
        this.itemId = itemId;
        this.name = itemName;
        this.savePath = savePath;
        this.count = count;
        this.orderPrice = orderPrice;
        this.orderStatus = orderStatus;
    }
}
