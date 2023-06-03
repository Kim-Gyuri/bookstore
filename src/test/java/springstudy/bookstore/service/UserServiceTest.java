package springstudy.bookstore.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import springstudy.bookstore.domain.dto.UserFormDto;
import springstudy.bookstore.domain.entity.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserServiceTest {
    @Autowired UserService userService;

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

    @Test
    @DisplayName("회원가입 테스트")
    public void saveUserTest() {
        UserFormDto dto = createUserTest();
        userService.signUp(dto);

        User findUser = userService.findOne(dto.getLoginId());

        assertEquals(dto.getLoginId(), findUser.getLoginId());
        assertEquals(dto.getPassword(), findUser.getPassword());
        assertEquals(dto.getCity(), findUser.getAddress().getCity());
    }

    @Test
    @DisplayName("중복 회원가입 테스트")
    public void savedDuplicateUserTest() {
        UserFormDto dto = createUserTest();
        userService.signUp(dto);

        UserFormDto dto2 = createUserTest();
        Throwable e = assertThrows(IllegalStateException.class, () -> {
            userService.signUp(dto2);
        });
        assertEquals("이미 존재하는 아이디입니다.", e.getMessage());
    }
}