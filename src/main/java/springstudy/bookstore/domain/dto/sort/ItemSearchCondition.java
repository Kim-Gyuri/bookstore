package springstudy.bookstore.domain.dto.sort;

import lombok.Data;
import springstudy.bookstore.domain.entity.User;

@Data
public class ItemSearchCondition {
    private String itemName;
    private User loginMember;
}
