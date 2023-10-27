package springstudy.bookstore.controller.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import springstudy.bookstore.controller.api.dto.sort.ItemSearch;
import springstudy.bookstore.controller.api.dto.sort.PageDtoTest;
import springstudy.bookstore.domain.dto.item.GetUserItemResponse;
import springstudy.bookstore.domain.dto.orderItem.GetOrderItemResponse;
import springstudy.bookstore.domain.dto.sales.SalesChat;
import springstudy.bookstore.domain.enums.CategoryType;
import springstudy.bookstore.service.SalesService;
import springstudy.bookstore.service.UserService;
import springstudy.bookstore.util.validation.argumentResolver.Login;
import springstudy.bookstore.util.validation.dto.SessionUser;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class SalesController {

    private final SalesService salesService;
    private final UserService userService;

    // 회원이 판매하는 상품정보/판매액 조회
    @GetMapping("/sales")
    public String mySales(@Login SessionUser sessionUser, @PageableDefault(size = 4) Pageable pageable, @ModelAttribute("itemSearch") ItemSearch search, Model model) {

        Page<GetUserItemResponse> products = salesService.searchItemByUserAndItemStatus(sessionUser.getLoginId(), search, pageable);

        for (GetUserItemResponse getUserItemResponse : products.getContent()) {
            log.info("item info = {}", getUserItemResponse.toString());
        }

        Page<GetOrderItemResponse> orderItems = salesService.findAllBySellerId_paging(sessionUser.getLoginId(), search.getOrderStatus(), pageable);
        List<SalesChat> salesChats = salesService.findByOrderDate(sessionUser.getLoginId());

        model.addAttribute("products", products.getContent());
        model.addAttribute("orderItems", orderItems.getContent());

        model.addAttribute("page", new PageDtoTest(products.getTotalElements(), search.getItemStatus(), pageable));
        model.addAttribute("orderItemPage", new PageDtoTest(orderItems.getTotalElements(), search.getOrderStatus(), pageable));

        model.addAttribute("salesChats", salesChats);

        model.addAttribute("revenuePerMonth_result", SalesChat.revenuePerMonth_result(salesChats));
        model.addAttribute("totalRevenue_result",SalesChat.totalRevenue_result(salesChats));

        model.addAttribute("categoryTypes", CategoryType.values());
         model.addAttribute("users",sessionUser);

        return "/sales/userProduct";
    }
}
