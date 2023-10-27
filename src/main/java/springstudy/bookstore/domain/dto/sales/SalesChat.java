package springstudy.bookstore.domain.dto.sales;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class SalesChat {

    private LocalDate orderDate;
    private int revenuePerMonth;

    @QueryProjection
    public SalesChat(LocalDate orderDate, int revenuePerMonth) {
        this.orderDate = orderDate;
        this.revenuePerMonth = revenuePerMonth;
    }


    public static int revenuePerMonth_result(List<SalesChat> salesChats) {
        LocalDate currentDate = LocalDate.now();

        return salesChats.stream()
                .filter(sale -> sale.getOrderDate().getYear() == currentDate.getYear() && sale.getOrderDate().getMonth() == currentDate.getMonth())
                .mapToInt(SalesChat::getRevenuePerMonth)
                .sum();
    }

    public static int totalRevenue_result(List<SalesChat> salesChats) {
        return salesChats.stream()
                .mapToInt(SalesChat::getRevenuePerMonth)
                .sum();
    }




}
