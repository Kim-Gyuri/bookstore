package springstudy.bookstore.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ItemUpdateForm {

    @NotBlank
    private String itemName;

    @NotNull
    private Integer price;

    @NotNull
    private Integer quantity;
}
