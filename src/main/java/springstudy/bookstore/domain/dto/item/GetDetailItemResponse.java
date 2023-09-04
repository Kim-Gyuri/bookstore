package springstudy.bookstore.domain.dto.item;

import lombok.Data;
import springstudy.bookstore.domain.dto.itemImg.GetItemImgResponse;
import springstudy.bookstore.domain.entity.Item;
import springstudy.bookstore.domain.enums.CategoryType;
import springstudy.bookstore.domain.enums.ItemSellStatus;
import springstudy.bookstore.domain.enums.ItemType;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
public class GetDetailItemResponse {
    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;
    private ItemType itemType;
    private CategoryType categoryType;
    private ItemSellStatus status;

    private Integer count;
    private List<GetItemImgResponse> itemImgDtoList;

    public GetDetailItemResponse(Item item) {
        this.itemName = item.getItemName();
        this.price = item.getPrice();
        this.quantity = item.getStockQuantity();
        this.itemType = item.getItemType();
        this.categoryType = item.getCategoryType();
        this.status = item.getStatus();
        this.itemImgDtoList = getImgDtoList(item);
    }

    private List<GetItemImgResponse> getImgDtoList(Item item) {
        return item.getImgList().stream()
                .map(itemImg -> GetItemImgResponse.of(itemImg))
                .collect(toList());
    }

}
