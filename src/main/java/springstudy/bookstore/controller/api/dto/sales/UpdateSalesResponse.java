package springstudy.bookstore.controller.api.dto.sales;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateSalesResponse {
    private Long id;
    private String itemName;
    private String main_img_path;
}
