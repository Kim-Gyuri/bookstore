package springstudy.bookstore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springstudy.bookstore.domain.dto.LoginFormDto;
import springstudy.bookstore.domain.dto.UserFormDto;
import springstudy.bookstore.domain.dto.UserMainItemDto;
import springstudy.bookstore.domain.entity.SessionConst;
import springstudy.bookstore.domain.entity.User;
import springstudy.bookstore.service.ItemService;
import springstudy.bookstore.service.UserService;
import springstudy.bookstore.util.validation.argumentResolver.Login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/bookstore")
public class UserController {

    private final UserService userService;
    private final ItemService itemService;

    @GetMapping("/signUp")
    public String signUpForm(Model model) {
        model.addAttribute("userForm", new UserFormDto());
        return "signUp/signUpForm";
    }

    @PostMapping("/signUp")
    public String signUp(UserFormDto userFormDto) {
        userService.signUp(userFormDto);
        return "redirect:/bookstore/login";
    }

    @GetMapping("/login")
    public String signInForm(@ModelAttribute("loginForm") LoginFormDto form) {
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String signIn(@Valid @ModelAttribute("loginForm") LoginFormDto form,
                         BindingResult bindingResult, HttpServletRequest request,
                         @RequestParam(defaultValue = "/bookstore/home") String redirectURL) {
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        User loginUser = userService.signIn(form);

        if (loginUser == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 다릅니다.");
            return "login/loginForm";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginUser);
        log.info("loginUser info{}", loginUser.toString());

        return "redirect:" + redirectURL;
    }

    @PostMapping("/logout")
    public String logoutV3(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/bookstore/home";
    }

    @GetMapping("/user/Info")
    public String UserInfo(@Login User loginUser, Model model) {

        if (loginUser == null) {
            return "login/loginForm";
        }

        log.info("get -> loginUser info{}", loginUser.toString());
        model.addAttribute("member", loginUser);
        log.info("user address ={}", loginUser.getAddress().getCity());

        return "users/userInfo";
    }

    @GetMapping("/user/product")
    public String UserProductInfo(@Login User loginUser, Model model) {
        if (loginUser == null) {
            return "login/loginForm";
        }

        List<UserMainItemDto> products = userService.findAllByUser(loginUser);
        model.addAttribute("products", products);
        return "users/userProduct";
    }


   @GetMapping("/user/product/delete/{itemId}")
    public String deleteOrderItem(@PathVariable("itemId") Long itemId, @Login User loginUser) {
       if (loginUser == null) {
           return "login/loginForm";
       }

       log.info("delete Item={}", itemService.findById(itemId).getItemName());
       userService.delete(itemId);
       return "redirect:/bookstore/user/product";
   }


}
