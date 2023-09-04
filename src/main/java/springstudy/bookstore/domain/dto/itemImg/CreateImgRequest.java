package springstudy.bookstore.domain.dto.itemImg;

import lombok.Data;
import springstudy.bookstore.domain.entity.Item;
import springstudy.bookstore.domain.enums.IsMainImg;

@Data
public class CreateImgRequest {
    private IsMainImg YN;
    private Item item;

   public CreateImgRequest(Item itemEntity) {
        YN = IsMainImg.N;
        item = itemEntity;
    }


}
