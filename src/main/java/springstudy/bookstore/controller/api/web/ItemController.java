package springstudy.bookstore.controller.api.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import springstudy.bookstore.domain.dto.item.CreateItemRequest;
import springstudy.bookstore.domain.dto.item.GetDetailItemResponse;
import springstudy.bookstore.domain.dto.item.GetPreViewItemResponse;
import springstudy.bookstore.domain.dto.itemImg.GetItemImgResponse;
import springstudy.bookstore.domain.dto.sort.ItemSearchCondition;
import springstudy.bookstore.domain.dto.sort.PageDto;
import springstudy.bookstore.domain.enums.CategoryType;
import springstudy.bookstore.domain.enums.ItemType;
import springstudy.bookstore.service.ItemImgService;
import springstudy.bookstore.service.ItemService;
@Slf4j
@RequiredArgsConstructor
@Controller
public class ItemController {

    private final ItemService itemService;
    private final ItemImgService imgService;

    @GetMapping("/items/add")
    public String addItemForm(Model model) {

        model.addAttribute("itemTypes", ItemType.values());
        model.addAttribute("categoryTypes", CategoryType.values());
        model.addAttribute("itemFormDto", new CreateItemRequest());

        return "item/addItemForm";
    }

   // @GetMapping("/items/{itemId}")
    public String itemDetail(@PathVariable Long itemId, Model model) {

        GetDetailItemResponse dto = itemService.getItemDetail(itemId);

        for (GetItemImgResponse itemImgResponse : dto.getItemImgDtoList()) {
            log.info("img imgName={} savePath={}", itemImgResponse.getImgName(),itemImgResponse.getSavePath());
        }

        model.addAttribute("item", dto);
        return "item/itemDetail";
    }

    @GetMapping("/items/{id}/edit")
    public String editItemForm(@PathVariable("id") Long id, Model model) {
        GetDetailItemResponse dto = itemService.getItemDetail(id);

        model.addAttribute("item", dto);
        log.info("확인용={}", dto.getItemImgDtoList().get(0).getId());
        return "item/editItemForm";
    }

    @DeleteMapping("/items/{itemId}/img/{imgId}")
    public void deleteItemImg(@PathVariable("itemId") Long itemId, @PathVariable("imgId") Long imgId) {
        itemService.deleteImg(itemId, imgId);
    }

    // 상품 클릭하여 들어가기 -> 클릭한 상품 구매페이지로 이동
    @GetMapping("/items/{id}")
    public String showOne(@PathVariable("id") Long itemId, Model model) {

        GetDetailItemResponse product = itemService.getItemDetail(itemId);

        model.addAttribute("product", product);
        return "product/productInfo";
    }
    @GetMapping("/items/category/{code}")
    public String showCategory(Model model,
                               @PageableDefault(size = 3) Pageable pageable,
                               @PathVariable("code") String code, @RequestParam(required = false) ItemSearchCondition condition) {

        Page<GetPreViewItemResponse> results;
        PageDto pageDto;

        if (condition.getItemName() == null) {
            results = itemService.categoryPageSort(code, pageable);
            pageDto = new PageDto(results.getTotalElements(), code, pageable);
        } else {
            results = itemService.searchAndCategory(condition, code, pageable);
            pageDto = new PageDto(results.getTotalElements(), code, pageable);
        }

        model.addAttribute("items", results.getContent());
        model.addAttribute("page", pageDto);
        model.addAttribute("condition", condition);

        return "category/categoryPage";
    }

}
