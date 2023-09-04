package springstudy.bookstore.controller.api.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteWishItemResponse {
    private boolean success;
    private String message;
}