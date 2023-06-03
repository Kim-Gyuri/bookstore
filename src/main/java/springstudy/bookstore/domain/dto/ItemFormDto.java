package springstudy.bookstore.domain.dto;

import lombok.Data;
import org.modelmapper.ModelMapper;
import springstudy.bookstore.domain.entity.Item;
import springstudy.bookstore.domain.enums.CategoryType;
import springstudy.bookstore.domain.enums.ItemSellStatus;
import springstudy.bookstore.domain.enums.ItemType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class ItemFormDto {

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

    private List<ItemImgDto> itemImgDtoList = new ArrayList<>();

    public Item toEntity() {
        return Item.itemBuilder()
                .itemName(itemName)
                .price(price)
                .stockQuantity(quantity)
                .itemType(itemType)
                .categoryType(categoryType)
                .status(status)
                .build();
    }

    private static ModelMapper modelMapper = new ModelMapper();

    public static ItemFormDto of(Item item) {
        return modelMapper.map(item,ItemFormDto.class);
    }

}
