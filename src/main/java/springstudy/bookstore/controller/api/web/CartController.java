package springstudy.bookstore.controller.api.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
        // 세션 회원정보가 없는 경우, 로그인 화면으로 이동된다.
        if (user == null) {
            return "login/loginForm";
        }

        model.addAttribute("cartList", cartService.cartFindByUserId(user.getLoginId()));
        return "cart/cartList";
    }


}
