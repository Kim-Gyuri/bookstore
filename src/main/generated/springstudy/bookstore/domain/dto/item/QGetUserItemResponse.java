package springstudy.bookstore.domain.dto.item;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * springstudy.bookstore.domain.dto.item.QGetUserItemResponse is a Querydsl Projection type for GetUserItemResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QGetUserItemResponse extends ConstructorExpression<GetUserItemResponse> {

    private static final long serialVersionUID = 1387213835L;

    public QGetUserItemResponse(com.querydsl.core.types.Expression<Long> itemId, com.querydsl.core.types.Expression<String> itemName, com.querydsl.core.types.Expression<Integer> price, com.querydsl.core.types.Expression<Integer> quantity, com.querydsl.core.types.Expression<String> imgName, com.querydsl.core.types.Expression<String> imgUrl, com.querydsl.core.types.Expression<springstudy.bookstore.domain.enums.ItemType> itemType, com.querydsl.core.types.Expression<springstudy.bookstore.domain.enums.CategoryType> categoryType) {
        super(GetUserItemResponse.class, new Class<?>[]{long.class, String.class, int.class, int.class, String.class, String.class, springstudy.bookstore.domain.enums.ItemType.class, springstudy.bookstore.domain.enums.CategoryType.class}, itemId, itemName, price, quantity, imgName, imgUrl, itemType, categoryType);
    }

}

