package springstudy.bookstore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springstudy.bookstore.domain.dto.LoginFormDto;
import springstudy.bookstore.domain.dto.UserFormDto;
import springstudy.bookstore.domain.dto.UserMainItemDto;
import springstudy.bookstore.domain.entity.Item;
import springstudy.bookstore.domain.entity.ItemImg;
import springstudy.bookstore.domain.entity.User;
import springstudy.bookstore.repository.ItemImgRepository;
import springstudy.bookstore.repository.ItemRepository;
import springstudy.bookstore.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ItemImgRepository itemImgRepository;

    public Long signUp(UserFormDto userFormDto) {
        validateDuplicateUser(userFormDto.getLoginId());
        return userRepository.save(userFormDto.userEntity()).getId();
    }

    private void validateDuplicateUser(String loginId) {
        Optional<User> byLoginId = userRepository.findByLoginId(loginId);
        if (byLoginId.isPresent()) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
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
    public List<UserMainItemDto> findAllByUser(User user) {
        List<UserMainItemDto> userMainItemDtoList = itemRepository.sortByUser();
        List<Item> getItemList = itemRepository.findAllByUser(user);

        List<UserMainItemDto> result = new ArrayList<>();
        for (Item item : getItemList) {
            for (UserMainItemDto userMainItemDto : userMainItemDtoList) {
                if (item.getId() == userMainItemDto.getItemId()) {
                    result.add(userMainItemDto);
                }
            }
        }
        return result;
    }

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
