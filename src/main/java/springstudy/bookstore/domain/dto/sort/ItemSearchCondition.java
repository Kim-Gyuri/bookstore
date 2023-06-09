package springstudy.bookstore.domain.dto.sort;

import lombok.Builder;
import lombok.Data;
import springstudy.bookstore.domain.entity.User;

@Data
@Builder
public class ItemSearchCondition {
    private String itemName;
    private User loginMember;
}
