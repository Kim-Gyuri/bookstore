package springstudy.bookstore.controller.api.dto.item;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteItemResponse {
    private boolean success;
    private String message;
}
