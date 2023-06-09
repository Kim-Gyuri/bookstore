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
import springstudy.bookstore.repository.ItemRepository;
import springstudy.bookstore.repository.OrderItemRepository;
import springstudy.bookstore.repository.UserRepository;
import springstudy.bookstore.util.exception.DuplicateOrderItemException;
import springstudy.bookstore.util.exception.UserNotFoundException;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class CartService {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional(readOnly = true)
    public Cart findById(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 없습니다."));
    }

    @Transactional(readOnly = true)
    public List<CartInfoDto> getWishList(String loginId) {
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다."));
        return user.getWishList();
    }

    @Transactional
    public void addWishList(String loginId, Long itemId, Integer count) {
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다."));

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다."));

        OrderItem orderItem = orderItemRepository.save(new OrderItem(user.getCart(), item, count));

        if (user.checkOrderItemDuplicate(orderItem)) {
            throw new DuplicateOrderItemException("중복된 장바구니입니다.");
        }

        user.addCartItem(orderItem);
    }

    @Transactional
    public void deleteWishList(Long orderItemId) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다."));

        orderItem.getItem().cancelCart(orderItem.getCount());
        orderItemRepository.delete(orderItem);
    }
}
