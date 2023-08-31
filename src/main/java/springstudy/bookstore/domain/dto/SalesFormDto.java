package springstudy.bookstore.domain.dto;

import lombok.Builder;
import lombok.Data;
import springstudy.bookstore.domain.entity.Item;
import springstudy.bookstore.domain.enums.CategoryType;
import springstudy.bookstore.domain.enums.ItemSellStatus;
import springstudy.bookstore.domain.enums.ItemType;

@Data
public class SalesFormDto {
    private String itemName;
    private Integer price;
    private Integer quantity;
    private ItemType itemType;
    private CategoryType categoryType;
    private ItemSellStatus status;
    private String mainImgSavePath;
    @Builder
    public SalesFormDto(Item item) {
        this.itemName = item.getItemName();
        this.price = item.getPrice();
        this.quantity = item.getStockQuantity();
        this.itemType = item.getItemType();
        this.categoryType = item.getCategoryType();
        this.status = item.getStatus();
        this.mainImgSavePath = item.getMainImg_path();
    }
}
