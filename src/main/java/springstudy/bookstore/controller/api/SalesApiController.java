package springstudy.bookstore.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springstudy.bookstore.domain.dto.sales.GetSalesResponse;
import springstudy.bookstore.service.SalesService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class SalesApiController {

    private final SalesService salesService;

    @GetMapping("/sales")
    public GetSalesResponse myItems(String loginId) {
        return salesService.findByUserLoginId(loginId);
    }
}
