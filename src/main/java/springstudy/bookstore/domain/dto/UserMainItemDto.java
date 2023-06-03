package springstudy.bookstore.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import springstudy.bookstore.domain.enums.CategoryType;
import springstudy.bookstore.domain.enums.ItemType;

@Data
public class UserMainItemDto {
    private Long itemId;
    private String itemName;
    private Integer price;
    private Integer quantity;
    private String imgName;
    private ItemType itemType;
    private CategoryType categoryType;

    @QueryProjection
    public UserMainItemDto(Long itemId, String itemName, Integer price, Integer quantity, String imgName, ItemType itemType, CategoryType categoryType) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
        this.imgName = imgName;
        this.itemType = itemType;
        this.categoryType = categoryType;
    }
}
