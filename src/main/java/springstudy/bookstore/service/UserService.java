package springstudy.bookstore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springstudy.bookstore.domain.dto.LoginFormDto;
import springstudy.bookstore.domain.dto.UserFormDto;
import springstudy.bookstore.domain.dto.UserMainItemDto;
import springstudy.bookstore.domain.entity.*;
import springstudy.bookstore.repository.*;
import springstudy.bookstore.util.exception.UserNotFoundException;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ItemImgRepository itemImgRepository;
    private final CartRepository cartRepository;
    private final SalesRepository salesRepository;

    public Long signUp(UserFormDto userFormDto) {
        validateDuplicateUser(userFormDto.getLoginId());
        User user = userRepository.save(userFormDto.userEntity());
        user.createCart(cartRepository.save(new Cart()));
        user.createSales(salesRepository.save(new Sales()));

        return user.getId();
    }

    @Transactional(readOnly = true)
    private void validateDuplicateUser(String loginId) {
        Optional<User> byLoginId = userRepository.findByLoginId(loginId);
        if (byLoginId.isPresent()) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
    }

    @Transactional(readOnly = true)
    public void existByLoginIdAndPassword(LoginFormDto loginFormDto) {
        if (!userRepository.existsByLoginIdAndPassword(loginFormDto.getLoginId(), loginFormDto.getPassword())) {
            throw new UserNotFoundException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }
    }

    public User signIn(LoginFormDto loginFormDto) {
        return userRepository.findByLoginId(loginFormDto.getLoginId())
                .filter(m -> m.getPassword().equals(loginFormDto.getPassword()))
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public User findOne(String loginId) {
        Optional<User> byLoginId = userRepository.findByLoginId(loginId);
        return byLoginId.orElseThrow(() -> new IllegalArgumentException("해당되는 아이디가 없습니다."));
    }

    @Transactional(readOnly = true)
    public User findById(long id) {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public User findByLoginId(String id) {
        return userRepository.findByLoginId(id)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다."));
    }


    @Transactional(readOnly = true)
    public User findByItemId(long id) {
        Item item_target = itemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다."));
        String seller_id = "";

        List<User> users = userRepository.findAll();
        for (User user : users) {
            for (Item item : user.getSales().getItemList()) {
                if (item.getId().equals(item_target.getId())) {
                    seller_id = user.getLoginId();
                }
            }
        }
        return findByLoginId(seller_id);
    }


    @Transactional(readOnly = true)
    public List<UserMainItemDto> findAllByUser(User user) {
        return itemRepository.sortByUser(user);
    }

    /*
    @Transactional(readOnly = true)
    public List<UserMainItemDto> findAllByUser(User user) {
        List<UserMainItemDto> userMainItemDtoList = itemRepository.sortByUser();
        List<Item> getItemList = itemRepository.findAllByUser(user);

        List<UserMainItemDto> result = new ArrayList<>();
        for (Item item : getItemList) {
            for (UserMainItemDto userMainItemDto : userMainItemDtoList) {
                if (item.getId() == userMainItemDto.getItemId()) {
                    log.info("회원이 등록한 상품 ={}", item.getItemName());
                    result.add(userMainItemDto);
                }
            }
        }
        return result;
    }

     */

    @Transactional
    public void delete(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다."));
        List<ItemImg> itemImgList = itemImgRepository.findAllByItem_id(itemId);
        itemRepository.delete(item);
        for (ItemImg itemImg : itemImgList) {
            itemImgRepository.delete(itemImg);
        }
    }

}
