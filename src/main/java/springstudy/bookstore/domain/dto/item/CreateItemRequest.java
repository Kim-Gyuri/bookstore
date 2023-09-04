package springstudy.bookstore.domain.dto.item;

import lombok.Data;
import springstudy.bookstore.domain.entity.Item;
import springstudy.bookstore.domain.enums.CategoryType;
import springstudy.bookstore.domain.enums.ItemType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateItemRequest {

    private Long id;

    private String uploaderId;

    @NotBlank
    private String itemName;

    @NotNull
    private Integer price;

    @NotNull
    private Integer quantity;

    private ItemType itemType;

    private CategoryType categoryType;

    public Item toEntity() {
        return Item.initItemBuilder()
                .sellerId(uploaderId)
                .itemName(itemName)
                .price(price)
                .stockQuantity(quantity)
                .itemType(itemType)
                .categoryType(categoryType)
                .build();
    }
}
