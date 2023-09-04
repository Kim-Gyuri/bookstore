package springstudy.bookstore.controller.api.dto.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import springstudy.bookstore.domain.dto.itemImg.GetItemImgResponse;
import springstudy.bookstore.domain.entity.Item;
import springstudy.bookstore.domain.entity.ItemImg;
import springstudy.bookstore.domain.enums.CategoryType;
import springstudy.bookstore.domain.enums.ItemSellStatus;
import springstudy.bookstore.domain.enums.ItemType;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class GetItemResponse {
    private Long id;
    private String name;
    private int price;
    private int quantity;
    private ItemType itemType;
    private CategoryType categoryType;
    private ItemSellStatus status;
    private List<GetItemImgResponse> itemImgDtoList;

    public GetItemResponse(Item item) {
        this.id = item.getId();
        this.name = item.getItemName();
        this.price = item.getPrice();
        this.quantity = item.getStockQuantity();
        this.itemType = item.getItemType();
        this.categoryType = item.getCategoryType();
        this.status = item.getStatus();
        getImgDtoList(item);
    }

    private void getImgDtoList(Item item) {
        itemImgDtoList = new ArrayList<>();
        for (ItemImg itemImg : item.getImgList()) {
            this.itemImgDtoList.add(GetItemImgResponse.of(itemImg));
        }
    }


}