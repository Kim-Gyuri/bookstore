package springstudy.bookstore.domain.dto.orderItem;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * springstudy.bookstore.domain.dto.orderItem.QGetOrderItemResponse is a Querydsl Projection type for GetOrderItemResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QGetOrderItemResponse extends ConstructorExpression<GetOrderItemResponse> {

    private static final long serialVersionUID = -988989942L;

    public QGetOrderItemResponse(com.querydsl.core.types.Expression<Long> orderItemId, com.querydsl.core.types.Expression<Long> itemId, com.querydsl.core.types.Expression<String> itemName, com.querydsl.core.types.Expression<String> savePath, com.querydsl.core.types.Expression<Integer> count, com.querydsl.core.types.Expression<Integer> orderPrice, com.querydsl.core.types.Expression<springstudy.bookstore.domain.enums.OrderStatus> orderStatus) {
        super(GetOrderItemResponse.class, new Class<?>[]{long.class, long.class, String.class, String.class, int.class, int.class, springstudy.bookstore.domain.enums.OrderStatus.class}, orderItemId, itemId, itemName, savePath, count, orderPrice, orderStatus);
    }

}

