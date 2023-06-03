package springstudy.bookstore.domain.dto;

import lombok.Data;
import springstudy.bookstore.domain.entity.Item;
import springstudy.bookstore.domain.enums.IsMainImg;

@Data
public class ItemInfoDto {
    private IsMainImg YN;
    private Item item;
}
