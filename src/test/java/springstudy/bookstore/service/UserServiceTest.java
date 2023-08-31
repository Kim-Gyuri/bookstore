package springstudy.bookstore.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import springstudy.bookstore.domain.dto.LoginFormDto;
import springstudy.bookstore.domain.dto.UserFormDto;
import springstudy.bookstore.domain.dto.UserMainItemDto;
import springstudy.bookstore.domain.entity.User;
import springstudy.bookstore.repository.ItemRepository;
import springstudy.bookstore.util.exception.UserNotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@Slf4j
@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    ItemRepository itemRepository;

    public UserFormDto createUserTest() {
        UserFormDto dto = new UserFormDto();
        dto.setLoginId("nana20");
        dto.setPassword("1234@");
        dto.setName("faker");
        dto.setEmail("karis99@naver.com");
        dto.setCity("서울");
        dto.setStreet("마포구 합정동");
        dto.setZipcode("1120");

        return dto;
    }

    public LoginFormDto createLoginDto() {
        return LoginFormDto.builder()
                .loginId("nana20")
                .password("1234@")
                .build();
    }

    public LoginFormDto createLoginDto_fail() {
        return LoginFormDto.builder()
                .loginId("nana20")
                .password("12341234@")
                .build();
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void saveUserTest() {
        // given : "faker" 이름의 사용자가 회원가입 하려고 한다.
        UserFormDto dto = createUserTest();
        userService.signUp(dto);

        // when : 로직 실행
        User findUser = userService.findOne(dto.getLoginId());

        // then : 회원 정보가 알맞게 등록되었는지 확인한다.
        assertEquals(dto.getLoginId(), findUser.getLoginId());
        assertEquals(dto.getPassword(), findUser.getPassword());
        assertEquals(dto.getCity(), findUser.getAddress().getCity());
    }

    @Test
    @DisplayName("중복 회원가입 테스트")
    public void savedDuplicateUserTest() {
        // given : "faker" 이름의 사용자가 회원가입을 했었다.
        UserFormDto dto = createUserTest();
        userService.signUp(dto);

        // then : 이때 중복 회원가입을 시도하면
        UserFormDto dto2 = createUserTest();
        Throwable e = assertThrows(IllegalStateException.class, () -> {
            userService.signUp(dto2);
        });

        // then : 중복 회원가입 예외가 발생하는지?
        assertEquals("이미 존재하는 아이디입니다.", e.getMessage());
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    public void loginTest() {
        // given : "faker" 이름의 사용자가 회원가입을 했을 때
        UserFormDto dto = createUserTest();
        userService.signUp(dto);

        // then : 로그인을 하려고 한다.
        LoginFormDto loginDto = createLoginDto();

        // then : DB에 회원정보가 있는지 확인한다.
        userService.existByLoginIdAndPassword(loginDto);
    }

    @Test
    @DisplayName("로그인 실패 테스트 - 비밀번호를 틀린 경우")
    public void loginTest_fail() {
        // given : "faker" 이름의 사용자가 회원가입 했을 때
        UserFormDto dto = createUserTest();
        userService.signUp(dto);

        // when : 회원이 (잘못된 비밀번호로) 로그인을 시도하려고 했을 때
        LoginFormDto loginDto = createLoginDto_fail();

        // then : 로그인 실패 예외처리가 되는지?
        Throwable e = assertThrows(UserNotFoundException.class, () -> {
            userService.existByLoginIdAndPassword(loginDto);
        });
        assertEquals("아이디 또는 비밀번호가 일치하지 않습니다.", e.getMessage());
    }

    @Test
    @DisplayName("내 정보보기 테스트")
    public void getUserInfoTest() {
        // given : "faker" 이름의 사용자가 회원가입 되어있다.
        UserFormDto dto = createUserTest();
        userService.signUp(dto);

        // then : 로그인 했을 때,
        LoginFormDto loginDto = createLoginDto();

        // then : 회원 정보가 알맞게 조회되는지?
        User user = userService.findOne(loginDto.getLoginId());
        assertEquals(dto.getName(), user.getName());
        assertEquals(dto.getEmail(), user.getEmail());
    }
    
    @Test
    void getItems() {
        User user = userService.findOne("test2");
        List<UserMainItemDto> list = userService.findAllByUser(user);

        for (UserMainItemDto dto : list) {
            log.info("확인ㅇㅇ={}", dto.getItemName());
        }

    }
}