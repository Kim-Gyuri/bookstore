package springstudy.bookstore.domain.dto.sales;

import lombok.Data;
import springstudy.bookstore.domain.dto.item.GetUserItemResponse;

import java.util.List;

@Data
public class GetSalesResponse {
    private List<GetUserItemResponse> itemList;
    private int totalRevenue;

    public GetSalesResponse(List<GetUserItemResponse> itemList, int totalRevenue) {
        this.itemList = itemList;
        this.totalRevenue = totalRevenue;
    }
}
