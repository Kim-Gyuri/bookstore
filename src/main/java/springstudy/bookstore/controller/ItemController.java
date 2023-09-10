package springstudy.bookstore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springstudy.bookstore.domain.dto.item.CreateItemRequest;
import springstudy.bookstore.domain.dto.item.GetDetailItemResponse;
import springstudy.bookstore.domain.dto.item.UpdateItemRequest;
import springstudy.bookstore.domain.dto.itemImg.GetItemImgResponse;
import springstudy.bookstore.domain.entity.ItemImg;
import springstudy.bookstore.domain.entity.User;
import springstudy.bookstore.domain.enums.CategoryType;
import springstudy.bookstore.domain.enums.ItemType;
import springstudy.bookstore.service.ItemImgService;
import springstudy.bookstore.service.ItemService;
import springstudy.bookstore.util.validation.argumentResolver.Login;

import java.io.IOException;
import java.util.List;

@Slf4j
//@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ItemImgService imgService;


    @GetMapping("/item/new")
    public String itemForm(Model model) {

        model.addAttribute("itemTypes", ItemType.values());
        model.addAttribute("categoryTypes", CategoryType.values());
        model.addAttribute("itemFormDto", new CreateItemRequest());

        return "item/addItemForm";
    }

    @PostMapping("/item/new")
    public String itemNew(@Login User loginUser, @Validated @ModelAttribute CreateItemRequest itemFormDto, BindingResult bindingResult,
                          Model model, @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList, RedirectAttributes redirectAttributes) throws IOException {

        if (loginUser == null) {
            return "login/loginForm";
        }

        log.info("post-> loginUser info{}", loginUser.toString());
        if (itemImgFileList.get(0).isEmpty()) {
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "item/addItemForm";
        }

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "item/addItemForm";
        }

        // 성공로직
        Long id = itemService.saveItem(loginUser, itemFormDto, itemImgFileList);
        log.info("itemInfo={}", itemService.findById(id).toString());
        redirectAttributes.addAttribute("itemId", id);
        return "redirect:/bookstore/item/{itemId}";
    }
/*
    @PostMapping("/item/new")
    public String itemNew_s3(@Login User loginUser, @Validated @ModelAttribute ItemFormDto itemFormDto, BindingResult bindingResult,
                          Model model, @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList, RedirectAttributes redirectAttributes) throws IOException {

        if (loginUser == null) {
            return "login/loginForm";
        }

        log.info("post-> loginUser info{}", loginUser.toString());
        if (itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null) {
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "item/addItemForm";
        }

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "item/addItemForm";
        }

        // 성공로직
        Long id = itemService.saveItem_s3(loginUser, itemFormDto, itemImgFileList);
        log.info("itemInfo={}", itemService.findById(id).toString());
        redirectAttributes.addAttribute("itemId", id);
        return "redirect:/bookstore/item/{itemId}";
    }

 */

    @GetMapping("/item/{itemId}")
    public String itemDetail(@PathVariable Long itemId, Model model) {

        GetDetailItemResponse dto = itemService.getItemDetail(itemId);

        for (GetItemImgResponse itemImgResponse : dto.getItemImgDtoList()) {
            log.info("img imgName={} savePath={}", itemImgResponse.getImgName(),itemImgResponse.getSavePath());
        }

        model.addAttribute("item", dto);
        return "item/itemDetail";
    }

    @GetMapping("/item/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {

        GetDetailItemResponse dto = itemService.getItemDetail(itemId);
        model.addAttribute("item", dto);
        return "item/editItemForm";
    }

    @PostMapping("/item/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @Validated @ModelAttribute("item") UpdateItemRequest form,
                       BindingResult bindingResult, @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList,
                       RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "item/editItemForm";
        }

        itemService.update(itemId, form, itemImgFileList);
        redirectAttributes.addAttribute("itemId", itemId);
        return "redirect:/bookstore/item/{itemId}";
    }

    @GetMapping("/itemImg/delete/{imgName}")
    public String deleteItemImg(@PathVariable String imgName, RedirectAttributes redirectAttributes) {

        ItemImg img = imgService.findByImgName(imgName);
        imgService.delete(img);
        redirectAttributes.addAttribute("itemId", img.getItem().getId());
        return "redirect:/bookstore/item/{itemId}/edit";
    }


}
