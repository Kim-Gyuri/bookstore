package springstudy.bookstore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springstudy.bookstore.domain.dto.cart.GetCartResponse;
import springstudy.bookstore.domain.entity.User;
import springstudy.bookstore.service.CartService;
import springstudy.bookstore.util.validation.argumentResolver.Login;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/bookstore")
public class CartController {

    private final CartService cartService;

    @PostMapping("/products/{itemId}")
    public String addWishList(@Login User loginUser, @PathVariable Long itemId, @RequestParam int count) {

        if (loginUser == null) {
            return "login/loginForm";
        }

        cartService.addWishList(loginUser.getLoginId(), itemId, count);

        return "redirect:/bookstore/user/cart";
    }

    @GetMapping("/user/cart")
    public String getWishList(Model model, @Login User loginUser) {
        if (loginUser == null) {
            model.addAttribute("message", "no User");
            return "login/loginForm";
        }

        model.addAttribute("member", loginUser);
        log.info("get loginMember{}", loginUser.toString());

        List<GetCartResponse> cartList = cartService.getWishList(loginUser.getLoginId());
        model.addAttribute("cartList", cartList);
        for (GetCartResponse cartDto : cartList) {
            log.info("cartDto Info-Item name={}", cartDto.getItemName());
        }
        return "cart/cartList";
    }

    @GetMapping("/user/cart/delete/{orderItemId}")
    public String deleteWishList(@PathVariable("orderItemId") Long orderItemId) {

        cartService.deleteWishList(orderItemId);
        return "redirect:/bookstore/user/cart";
    }


}
