package springstudy.bookstore.controller.api.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import springstudy.bookstore.domain.dto.item.GetPreViewItemResponse;
import springstudy.bookstore.domain.dto.sort.ItemSearchCondition;
import springstudy.bookstore.domain.dto.sort.PageDto;
import springstudy.bookstore.service.ItemService;
import springstudy.bookstore.util.validation.argumentResolver.Login;
import springstudy.bookstore.util.validation.dto.SessionUser;

import java.net.MalformedURLException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CommonController {

    private final ItemService itemService;

    @GetMapping("/items")
    public String findAll(Model model, @Login SessionUser loginUser,
                          @PageableDefault(size = 4) Pageable pageable,
                          @RequestParam(required = false, name = "code") String code,
                          ItemSearchCondition condition) {

        Page<GetPreViewItemResponse> results;
        PageDto pageDto;

        if (loginUser == null) {
            return "login/loginForm";
        }

        if (code == null) {
            results = itemService.searchPageSort(condition, pageable);
            pageDto = new PageDto(results.getTotalElements(), pageable);
        } else {
            results = itemService.itemPriceSort(code, pageable);
            pageDto = new PageDto(results.getTotalElements(), code, pageable);
        }

        for (GetPreViewItemResponse result : results) {
            log.info("items info-itemImg={}", result.getImgName());

            String fullPath = result.getImgUrl();
            log.info("file path check={}", fullPath);
        }
        log.info(pageDto.toString());
        log.info("page sortParam ={}", pageDto.getSortParam());

        model.addAttribute("items", results.getContent());
        model.addAttribute("page", pageDto);
        model.addAttribute("condition", condition);

        return "shop/index";
    }


    @ResponseBody
    @GetMapping("{fileId}")
    public Resource download(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource(filename);
    }


}