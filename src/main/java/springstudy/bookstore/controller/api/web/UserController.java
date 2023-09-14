package springstudy.bookstore.controller.api.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springstudy.bookstore.controller.api.dto.user.GetUserResponse;
import springstudy.bookstore.domain.entity.User;
import springstudy.bookstore.domain.enums.CategoryType;
import springstudy.bookstore.service.UserService;
import springstudy.bookstore.util.validation.argumentResolver.Login;
import springstudy.bookstore.util.validation.dto.SessionUser;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // 로그인 양식 폼
    @GetMapping("/login")
    public String loginForm() {
        return "login/loginForm";
    }

    // 회원가입 양식 폼
    @GetMapping("/signUp")
    public String signUpForm() {
        return "signUp/signUpForm";
    }

    // 회원 정보 조회
    @GetMapping("/info")
    public String userInfo(@Login SessionUser sessionUser, Model model) {

        User user = userService.findByLoginId(sessionUser.getLoginId());

        model.addAttribute("categoryTypes", CategoryType.values());
        model.addAttribute("users",new GetUserResponse(user));
        return "users/userInfo";
    }

}
