package springstudy.bookstore.domain.dto.item;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdateItemRequest {
    @NotBlank
    private String itemName;
    @NotNull
    private Integer price;
    @NotNull
    private Integer quantity;
}
