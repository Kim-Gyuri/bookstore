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

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class CartService {

    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;
    private final OrderItemRepository orderItemRepository;

    public Cart findById(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 없습니다."));
    }

    public List<CartInfoDto> getCartList(User user) {
        List<Cart> carts = cartRepository.findByUser(user);

        List<CartInfoDto> dtoList = new ArrayList<>();
        for (Cart cart : carts) {
            for (OrderItem orderItem : cart.getOrderItemList()) {
                CartInfoDto cartDto = new CartInfoDto();

                cartDto.updateCartInfo(orderItem.getId(), user.getLoginId(), orderItem.getItem(), orderItem.getCount(), orderItem.getOrderPrice());
                log.info("update Dto={}", cartDto.getItem().getStockQuantity());
                dtoList.add(cartDto);
            }
        }
        return dtoList;
    }

    @Transactional
    public Long mergeCart(User user, Long itemId, Integer count) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다."));

        OrderItem orderItem = OrderItem.orderItemBuilder()
                .item(item)
                .count(count)
                .build();

        orderItem.orderAmount(count);
        Cart cart = Cart.createCart(user, orderItem);

        cartRepository.save(cart);
        return cart.getId();
    }

    @Transactional
    public void deleteCart(Long orderItemId) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다."));

        orderItem.getItem().cancelCart(orderItem.getCount());
        orderItemRepository.delete(orderItem);
    }
}
