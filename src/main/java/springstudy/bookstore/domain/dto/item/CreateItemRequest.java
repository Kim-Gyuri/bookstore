package springstudy.bookstore.domain.dto.item;

import lombok.Data;
import springstudy.bookstore.domain.entity.Item;
import springstudy.bookstore.domain.enums.CategoryType;
import springstudy.bookstore.domain.enums.ItemType;

@Data
public class CreateItemRequest {

    private String name;

    private Integer price;

    private Integer stockQuantity;

    private String itemType;

    private String categoryType;

    public Item toEntity(String userLoginId) {
        return Item.initItemBuilder()
                .sellerId(userLoginId)
                .itemName(name)
                .price(price)
                .stockQuantity(stockQuantity)
                .itemType(ItemType.valueOf(itemType))
                .categoryType(CategoryType.valueOf(categoryType))
                .build();
    }
}
