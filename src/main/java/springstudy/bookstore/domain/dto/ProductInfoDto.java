package springstudy.bookstore.domain.dto;

import lombok.Data;
import springstudy.bookstore.domain.enums.CategoryType;
import springstudy.bookstore.domain.enums.ItemSellStatus;
import springstudy.bookstore.domain.enums.ItemType;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductInfoDto {
    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;
    private ItemType itemType;
    private CategoryType categoryType;
    private ItemSellStatus status;

    private Integer count;
    private List<ItemImgDto> itemImgDtoList = new ArrayList<>();

    public void updateProductInfo(String itemName, Integer price, Integer quantity, ItemType itemType, CategoryType categoryType, ItemSellStatus status, List<ItemImgDto> itemImgDtoList) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
        this.itemType = itemType;
        this.categoryType = categoryType;
        this.status = status;
        this.itemImgDtoList = itemImgDtoList;

    }

}
