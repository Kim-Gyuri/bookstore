package springstudy.bookstore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springstudy.bookstore.domain.dto.CartInfoDto;
import springstudy.bookstore.domain.entity.Cart;
import springstudy.bookstore.domain.entity.Item;
import springstudy.bookstore.domain.entity.OrderItem;
import springstudy.bookstore.domain.entity.User;
import springstudy.bookstore.repository.CartRepository;
import springstudy.bookstore.repository.OrderItemRepository;
import springstudy.bookstore.util.exception.DuplicateOrderItemException;

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
                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 없습니다."));
    }

    @Transactional(readOnly = true)
    public List<CartInfoDto> getWishList(String loginId) {
        User user = userService.findByLoginId(loginId);
        return user.getWishList();
    }

    @Transactional
    public void addWishList(String loginId, Long itemId, Integer count) {
        User buyer = userService.findByLoginId(loginId); // 구매자

        Item item = itemService.findById(itemId); // (구매자가 주문요청한 상품)

        User seller = userService.findByItemId(itemId); // 판매자

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
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다."));

        orderItem.getItem().cancelCart(orderItem.getCount()); // 구매자 장바구니에서 삭제

        orderItem.getItem().getUser().getSales().cancelOrder(orderItem.getOrderPrice()); // 주문 취소요청 받음

        orderItemRepository.delete(orderItem);
    }
}
