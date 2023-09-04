package springstudy.bookstore.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springstudy.bookstore.controller.api.dto.cart.DeleteWishItemResponse;
import springstudy.bookstore.controller.api.dto.cart.CreateWishItemResponse;
import springstudy.bookstore.domain.dto.cart.GetCartResponse;
import springstudy.bookstore.service.CartService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CartApiController {

    private final CartService cartService;
    @GetMapping("/api/carts/")
    public List<GetCartResponse> getWishList(String userId) {
        return cartService.getWishList(userId);
    }

    // 장바구니에 담기
    @PostMapping("/api/items/{itemId}")
    public CreateWishItemResponse addWishList( String loginId, @PathVariable Long itemId, @RequestParam int count) {
        cartService.addWishList(loginId, itemId, count);

        return new CreateWishItemResponse(Boolean.TRUE, "success!");
    }

    // 장바구니 담은 상품삭제
    @DeleteMapping("/api/carts/{id}")
    public DeleteWishItemResponse deleteWishList(@PathVariable("id") Long orderItemId) {
        cartService.deleteWishList(orderItemId);
        return new DeleteWishItemResponse(Boolean.TRUE, "success!");
    }

}
