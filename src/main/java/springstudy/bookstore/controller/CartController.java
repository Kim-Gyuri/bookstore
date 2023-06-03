package springstudy.bookstore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springstudy.bookstore.domain.dto.CartInfoDto;
import springstudy.bookstore.domain.entity.Cart;
import springstudy.bookstore.domain.entity.OrderItem;
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
    public String mergeCart(@Login User loginUser, @PathVariable Long itemId, @RequestParam int count) {

        if (loginUser == null) {
            return "login/loginForm";
        }

        Long cartId = cartService.mergeCart(loginUser, itemId, count);

        Cart savedCart = cartService.findById(cartId);
        log.info("order Info-count={}", count);
        for (OrderItem orderItem : savedCart.getOrderItemList()) {
            log.info("item Info-quantity={}", orderItem.getItem().getStockQuantity());
            log.info("order Info-order price", orderItem.getOrderPrice());
        }
        return "redirect:/bookstore/user/cart";
    }

    @GetMapping("/user/cart")
    public String getCart(Model model, @Login User loginUser) {
        if (loginUser == null) {
            model.addAttribute("message", "no User");
            return "login/loginForm";
        }

        model.addAttribute("member", loginUser);
        log.info("get loginMember{}", loginUser.toString());

        List<CartInfoDto> cartList = cartService.getCartList(loginUser);
        model.addAttribute("cartList", cartList);
        for (CartInfoDto cartDto : cartList) {
            log.info("cartDto Info-Item name={}", cartDto.getItem().getItemName());
        }
        return "cart/cartList";
    }

    @GetMapping("/user/cart/delete/{orderItemId}")
    public String deleteOrderItem(@PathVariable("orderItemId") Long orderItemId) {

        cartService.deleteCart(orderItemId);
        return "redirect:/bookstore/user/cart";
    }
}
