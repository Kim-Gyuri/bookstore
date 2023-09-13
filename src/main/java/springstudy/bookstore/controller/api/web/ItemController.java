package springstudy.bookstore.controller.api.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import springstudy.bookstore.domain.dto.item.CreateItemRequest;
import springstudy.bookstore.domain.dto.item.GetDetailItemResponse;
import springstudy.bookstore.domain.dto.item.GetPreViewItemResponse;
import springstudy.bookstore.domain.dto.sort.PageDto;
import springstudy.bookstore.domain.enums.CategoryType;
import springstudy.bookstore.domain.enums.ItemType;
import springstudy.bookstore.service.ItemService;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ItemController {

    private final ItemService itemService;

    // 상품 등록 폼 화면
    @GetMapping("/items/add")
    public String addItemForm(Model model) {

        model.addAttribute("itemTypes", ItemType.values());
        model.addAttribute("categoryTypes", CategoryType.values());
        model.addAttribute("itemFormDto", new CreateItemRequest());

        return "item/addItemForm";
    }

    // 상품 수정 폼 화면
    @GetMapping("/items/{id}/edit")
    public String editItemForm(@PathVariable("id") Long id, Model model) {
        GetDetailItemResponse dto = itemService.getItemDetail(id);

        model.addAttribute("item", dto);
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
    public String showOne(@PathVariable("id") Long itemId, Model model) {

        GetDetailItemResponse product = itemService.getItemDetail(itemId);

        model.addAttribute("product", product);
        return "product/productInfo";
    }

    // 상품 카테고리별 정렬 페이지
    @GetMapping("/items/category/{code}")
    public String showCategory(
                               @PageableDefault(size = 3) Pageable pageable,
                               @PathVariable String code,
                               @RequestParam(required = false) String itemName, Model model) {

        Page<GetPreViewItemResponse> results;
        PageDto pageDto;

        if (StringUtils.isEmpty(itemName)) {
            results = itemService.categoryPageSort(code, pageable);
        } else {
            results = itemService.searchAndCategory(itemName, code, pageable);
        }
        pageDto = new PageDto(results.getTotalElements(), code, pageable);

        model.addAttribute("items", results.getContent());
        model.addAttribute("page", pageDto);
        model.addAttribute("condition", itemName);

        return "category/categoryPage";
    }

}
