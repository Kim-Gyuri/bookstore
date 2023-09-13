package springstudy.bookstore.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springstudy.bookstore.controller.api.dto.cart.CreateWishItemResponse;
import springstudy.bookstore.controller.api.dto.cart.DeleteWishItemResponse;
import springstudy.bookstore.domain.dto.orderItem.GetOrderItemResponse;
import springstudy.bookstore.service.CartService;
import springstudy.bookstore.util.validation.argumentResolver.Login;
import springstudy.bookstore.util.validation.dto.SessionUser;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartApiController {

    private final CartService cartService;

    // 장바구니 조회
    @GetMapping
    public List<GetOrderItemResponse> getWishList(@Login SessionUser user) {
       return cartService.cartFindByUserId(user.getLoginId());
    }

    // 장바구니에 상품 담기
    @PostMapping("/item/{itemId}/{count}")
    public CreateWishItemResponse addWishList(@Login SessionUser user, @PathVariable Long itemId, @PathVariable int count) {
        cartService.addWishList(user.getLoginId(), itemId, count);

        return new CreateWishItemResponse(Boolean.TRUE, "success!");
    }

    // 장바구니에 담긴 상품 삭제
    @DeleteMapping("/item/{id}")
    public DeleteWishItemResponse deleteWishList(@PathVariable("id") Long orderItemId) {
        cartService.deleteWishList(orderItemId);
        return new DeleteWishItemResponse(Boolean.TRUE, "success!");
    }

}
