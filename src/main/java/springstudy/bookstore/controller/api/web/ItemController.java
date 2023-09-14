package springstudy.bookstore.controller.api.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import springstudy.bookstore.controller.api.dto.sort.ItemSearch;
import springstudy.bookstore.domain.dto.item.CreateItemRequest;
import springstudy.bookstore.domain.dto.item.GetDetailItemResponse;
import springstudy.bookstore.domain.dto.item.GetPreViewItemResponse;
import springstudy.bookstore.controller.api.dto.sort.PageDto;
import springstudy.bookstore.domain.enums.CategoryType;
import springstudy.bookstore.domain.enums.ItemType;
import springstudy.bookstore.service.ItemService;
import springstudy.bookstore.util.validation.argumentResolver.Login;
import springstudy.bookstore.util.validation.dto.SessionUser;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ItemController {

    private final ItemService itemService;

    // 상품 등록 폼 화면
    @GetMapping("/items/add")
    public String addItemForm(@Login SessionUser user, Model model) {

        model.addAttribute("itemTypes", ItemType.values());
        model.addAttribute("categoryTypes", CategoryType.values());
        model.addAttribute("itemFormDto", new CreateItemRequest());
        model.addAttribute("user", user);
        return "item/addItemForm";
    }

    // 상품 수정 폼 화면
    @GetMapping("/items/{id}/edit")
    public String editItemForm(@Login SessionUser user, @PathVariable("id") Long id, Model model) {
        GetDetailItemResponse dto = itemService.getItemDetail(id);

        model.addAttribute("item", dto);
        model.addAttribute("user", user);
        log.info("확인용={}", dto.getItemImgDtoList().get(0).getId());
        return "item/editItemForm";
    }

    // (상품 수정 중) 상품 이미지 삭제
    @DeleteMapping("/items/{itemId}/img/{imgId}")
    public void deleteItemImg(@PathVariable("itemId") Long itemId, @PathVariable("imgId") Long imgId) {
        itemService.deleteImg(itemId, imgId);
    }

    // 상품 "구매하기" 버튼을 클릭하여 들어왔을 때, 클릭한 상품 구매페이지로 이동된다.
    @GetMapping("/items/{id}")
    public String showOne(@Login SessionUser user, @PathVariable("id") Long itemId, Model model) {

        GetDetailItemResponse product = itemService.getItemDetail(itemId);

        model.addAttribute("product", product);
        model.addAttribute("user", user);
        return "product/productInfo";
    }

    // 상품 카테고리별 정렬 페이지
    @GetMapping("/items/category/{code}")
    public String showCategory(@Login SessionUser user,
                               @PageableDefault(size = 3) Pageable pageable,
                               @PathVariable String code,
                               @ModelAttribute("itemSearch") ItemSearch itemSearch, Model model) {

        Page<GetPreViewItemResponse> results;
        PageDto pageDto;

        if (StringUtils.isEmpty(itemSearch.getItemName())) {
            results = itemService.categoryPageSort(code, pageable);
        } else {
            results = itemService.searchAndCategory(itemSearch, code, pageable);
        }
        pageDto = new PageDto(results.getTotalElements(), code, pageable);

        model.addAttribute("items", results.getContent());
        model.addAttribute("page", pageDto);
        model.addAttribute("categoryTypes", CategoryType.values());
        model.addAttribute("user", user);
        return "category/categoryPage";
    }

}
