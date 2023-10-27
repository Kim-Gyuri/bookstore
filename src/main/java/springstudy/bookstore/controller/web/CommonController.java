package springstudy.bookstore.controller.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springstudy.bookstore.controller.api.dto.sort.ItemSearch;
import springstudy.bookstore.domain.dto.item.GetPreViewItemResponse;
import springstudy.bookstore.controller.api.dto.sort.PageDto;
import springstudy.bookstore.domain.enums.CategoryType;
import springstudy.bookstore.service.ItemService;
import springstudy.bookstore.util.validation.argumentResolver.Login;
import springstudy.bookstore.util.validation.dto.SessionUser;

import java.net.MalformedURLException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CommonController {

    private final ItemService itemService;

    // 메인 페이지
    @GetMapping("/items")
    public String findAll(@Login SessionUser loginUser,
                          @PageableDefault(size = 4) Pageable pageable,
                          @RequestParam(required = false, name = "code") String code, // 가격 정렬기준
                          @ModelAttribute("itemSearch") ItemSearch itemSearch, Model model) {

        Page<GetPreViewItemResponse> results;
        PageDto pageDto;

        if (code == null) {
            results = itemService.searchPageSort(itemSearch, pageable);
        } else {
            results = itemService.itemPriceSort(code, pageable);
        }

        pageDto = new PageDto(results.getTotalElements(), code, pageable);

        log.info("itemName={}", itemSearch.getItemName());
        log.info(pageDto.toString());
        log.info("page sortParam ={}", pageDto.getSortParam());

        model.addAttribute("items", results.getContent());
        model.addAttribute("page", pageDto);
        model.addAttribute("categoryTypes", CategoryType.values());
        model.addAttribute("user", loginUser);
        return "shop/index";
    }

   // 모든 이미지 경로 찾기
    @ResponseBody
    @GetMapping("{fileId}")
    public Resource download(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource(filename);
    }


}