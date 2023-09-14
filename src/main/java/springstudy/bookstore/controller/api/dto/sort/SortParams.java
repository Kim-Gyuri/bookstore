package springstudy.bookstore.controller.api.dto.sort;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SortParams {
    private String code;
    private String displayName;
}
