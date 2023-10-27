package springstudy.bookstore.controller.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springstudy.bookstore.domain.enums.CategoryType;
import springstudy.bookstore.service.CartService;
import springstudy.bookstore.util.validation.argumentResolver.Login;
import springstudy.bookstore.util.validation.dto.SessionUser;
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    // 장바구니 조회
    @GetMapping
    public String myCart(@Login SessionUser user, Model model) {
        model.addAttribute("cartList", cartService.cartFindByUserId(user.getLoginId()));
        model.addAttribute("categoryTypes", CategoryType.values());
        model.addAttribute("user", user);
        return "cart/cartList";
    }


}
