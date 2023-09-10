package springstudy.bookstore.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springstudy.bookstore.domain.dto.sales.GetSalesResponse;
import springstudy.bookstore.service.SalesService;
import springstudy.bookstore.util.validation.argumentResolver.Login;
import springstudy.bookstore.util.validation.dto.SessionUser;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class SalesApiController {

    private final SalesService salesService;

    @GetMapping("/sales")
    public GetSalesResponse myItems(@Login SessionUser user) {
        return salesService.findByUserLoginId(user.getLoginId());
    }
}
