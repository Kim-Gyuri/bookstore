package springstudy.bookstore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import springstudy.bookstore.domain.dto.item.GetPreViewItemResponse;
import springstudy.bookstore.domain.dto.sort.ItemSearchCondition;
import springstudy.bookstore.domain.dto.sort.PageDto;
import springstudy.bookstore.service.ItemService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/bookstore")
public class CategoryController {

    private final ItemService itemService;

    @GetMapping("/category/{code}")
    public String showCategory(Model model,
                               @PageableDefault(size = 3) Pageable pageable,
                               @PathVariable("code") String code, ItemSearchCondition condition) {

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
