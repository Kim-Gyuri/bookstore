package springstudy.bookstore.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springstudy.bookstore.controller.api.dto.user.CreateUserResponse;
import springstudy.bookstore.controller.api.dto.user.GetUserResponse;
import springstudy.bookstore.controller.api.dto.user.LoginResponse;
import springstudy.bookstore.controller.api.dto.user.LogoutResponse;
import springstudy.bookstore.domain.dto.user.CreateUserRequest;
import springstudy.bookstore.domain.dto.user.LoginRequest;
import springstudy.bookstore.domain.entity.User;
import springstudy.bookstore.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserApiController {

    private final UserService userService;

    /** 추가할 것
     * 이메일 중복 검사 ?
     */

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CreateUserResponse createUser(CreateUserRequest dto) {
        Long id = userService.signUp(dto);

        User user = userService.findById(id);

        return new CreateUserResponse(user.getName(), user.getEmail(), user.getPassword());
    }

    /**
     * 로그인 성공
     *  /실패
     */
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public LoginResponse login(LoginRequest dto) {
        User user = userService.signIn(dto);

        return new LoginResponse(user.getLoginId(), user.getPassword());
    }

    /**
     * 로그아웃 성공
     *  /실패
     */
    @PostMapping("/logout")
    public LogoutResponse logout(HttpServletRequest request) {
        // 세션을 없애기 위해 null을 반환하도록 false를 넣느다.
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate(); // 세션 데이터 삭제
        }

        return new LogoutResponse(Boolean.TRUE, "logout success!");
    }

    // 추가할 것?
    // 비밀번호 찾기 -> 이메일 인증(토큰)
    // 회원 정보 수정

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/info")
    public GetUserResponse UserInfo(String loginId) {
        User user = userService.findByLoginId(loginId);

        return new GetUserResponse(user.getEmail(), user.getName(), user.getAddress().getCity(), user.getSales().getTotalRevenue());
    }
}
