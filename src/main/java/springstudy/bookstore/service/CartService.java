package springstudy.bookstore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springstudy.bookstore.domain.dto.cart.GetCartResponse;
import springstudy.bookstore.domain.entity.Cart;
import springstudy.bookstore.domain.entity.Item;
import springstudy.bookstore.domain.entity.OrderItem;
import springstudy.bookstore.domain.entity.User;
import springstudy.bookstore.repository.CartRepository;
import springstudy.bookstore.repository.OrderItemRepository;
import springstudy.bookstore.util.exception.cart.DuplicateOrderItemException;
import springstudy.bookstore.util.exception.cart.NotFoundOrderItemException;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class CartService {

    private final UserService userService;
    private final ItemService itemService;
    private final CartRepository cartRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional(readOnly = true)
    public Cart findById(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new NotFoundOrderItemException("해당 주문이 없습니다."));
    }

    @Transactional(readOnly = true)
    public List<GetCartResponse> getWishList(String loginId) {
        User user = userService.findByLoginId(loginId);
        return user.getWishList();
    }

    @Transactional
    public void addWishList(String loginId, Long itemId, Integer count) {
        User buyer = userService.findByLoginId(loginId); // 구매자

        Item item = itemService.findById(itemId); // (구매자가 주문요청한 상품)

        User seller = userService.findByLoginId(item.getSellerId());

        OrderItem orderItem = orderItemRepository.save(new OrderItem(buyer.getCart(), item, count)); // (장바구니에 담길 상품정보)

        if (buyer.checkOrderItemDuplicate(orderItem)) {
            throw new DuplicateOrderItemException("중복된 장바구니입니다.");
        }

        buyer.addCartItem(orderItem); // 장바구니에 추가
        seller.searchSales().takeOrder(orderItem.getOrderPrice());
    }

    @Transactional
    public void deleteWishList(Long orderItemId) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new NotFoundOrderItemException("해당 상품이 없습니다.")); // 예외처리; 장바구니에 넣은 상품이 없는 경우라면

        orderItem.getItem().cancelCart(orderItem.getCount()); // 장바구니 있는 상품 취소요청 보냄

        User seller = userService.findByLoginId(orderItem.getItem().getSellerId()); // (판매자가 환불을 해줘야 한다.)
        seller.getSales().cancelOrder(orderItem.getOrderPrice()); // 주문 취소요청 받음

        orderItemRepository.delete(orderItem); // 장바구니에서 해당 상품 삭제
    }
}
