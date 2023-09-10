package springstudy.bookstore.controller.api.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springstudy.bookstore.domain.dto.cart.GetCartResponse;
import springstudy.bookstore.domain.entity.User;
import springstudy.bookstore.service.CartService;
import springstudy.bookstore.service.UserService;
import springstudy.bookstore.util.validation.argumentResolver.Login;
import springstudy.bookstore.util.validation.dto.SessionUser;

import java.util.List;
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private CartService cartService;
    private UserService userService;

    // 장바구니 조회
    @GetMapping
    public String myCart(@Login SessionUser user, Model model) {
        List<GetCartResponse> cartList = cartService.getWishList(user.getLoginId());

        model.addAttribute("cartList", cartList);
        for (GetCartResponse dto : cartList) {
            log.info("cart info={}", dto.toString());
        }
        return "cart/cartList";
    }

    //@PostMapping("/{itemId}")
    public String addWishList(@Login User loginUser, @PathVariable Long itemId, @RequestParam int count) {

        cartService.addWishList(loginUser.getLoginId(), itemId, count);

        return "redirect:/carts";
    }

}
