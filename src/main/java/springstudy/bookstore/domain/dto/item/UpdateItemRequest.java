package springstudy.bookstore.domain.dto.item;

import lombok.Data;

import jakarta.validation.constraints.*;
@Data
public class UpdateItemRequest {
    @NotBlank
    private String itemName;
    @NotNull
    private Integer price;
    @NotNull
    private Integer quantity;
}
