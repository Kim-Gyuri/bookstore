package springstudy.bookstore.controller.api.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import springstudy.bookstore.domain.dto.sales.GetSalesResponse;
import springstudy.bookstore.service.SalesService;
import springstudy.bookstore.util.validation.argumentResolver.Login;
import springstudy.bookstore.util.validation.dto.SessionUser;

@Controller
@RequiredArgsConstructor
public class SalesController {

    private final SalesService salesService;

    @GetMapping("/sales")
    public String mySales(@Login SessionUser user, Model model) {
        GetSalesResponse dto = salesService.findByUserLoginId(user.getLoginId());

        model.addAttribute("products", dto);
        return "users/userProduct";
    }
}
