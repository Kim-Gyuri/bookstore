package springstudy.bookstore.domain.dto.item;

import jakarta.validation.constraints.*;
import lombok.Data;
import springstudy.bookstore.domain.dto.itemImg.GetItemImgResponse;
import springstudy.bookstore.domain.enums.CategoryType;
import springstudy.bookstore.domain.enums.ItemSellStatus;
import springstudy.bookstore.domain.enums.ItemType;

import java.util.List;

@Data
public class GetItemResponse {
    private Long id;
    @NotBlank
    private String itemName;
    @NotNull
    private Integer price;
    @NotNull
    private Integer quantity;
    private ItemType itemType;
    private CategoryType categoryType;
    private ItemSellStatus status;
    private List<GetItemImgResponse> itemImgDtoList;

}
