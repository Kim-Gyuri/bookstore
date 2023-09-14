package springstudy.bookstore.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springstudy.bookstore.controller.api.dto.item.DeleteItemResponse;
import springstudy.bookstore.controller.api.dto.item.GetItemResponse;
import springstudy.bookstore.controller.api.dto.sales.CreateSalesResponse;
import springstudy.bookstore.controller.api.dto.sales.UpdateSalesResponse;
import springstudy.bookstore.controller.api.dto.sort.ItemSearch;
import springstudy.bookstore.domain.dto.item.CreateItemRequest;
import springstudy.bookstore.domain.dto.item.GetPreViewItemResponse;
import springstudy.bookstore.domain.dto.item.UpdateItemRequest;
import springstudy.bookstore.domain.entity.Item;
import springstudy.bookstore.domain.entity.User;
import springstudy.bookstore.service.ItemService;
import springstudy.bookstore.service.SalesService;
import springstudy.bookstore.service.UserService;
import springstudy.bookstore.util.validation.argumentResolver.Login;
import springstudy.bookstore.util.validation.dto.SessionUser;

import java.io.IOException;
import java.util.List;
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/items")
public class ItemApiController {

    private final SalesService salesService;
    private final UserService userService;
    private final ItemService itemService;

    // 판매하고 싶은 상품 등록하기
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CreateSalesResponse uploadItem(@Login SessionUser sessionUser,
                                          @Validated @RequestPart(value="createItemRequest") CreateItemRequest dto,
                                          @RequestParam("images") List<MultipartFile> files) throws IOException {
        User seller = userService.findByLoginId(sessionUser.getLoginId()); // 판매자 회원

        Long id = salesService.uploadItem(seller, dto, files); // 상품 등록

        return new CreateSalesResponse(id);
    }

    // 등록한 상품 수정하기
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public UpdateSalesResponse updateItem(@PathVariable("id") Long id,
                                          @Validated @RequestPart(value = "updateItemRequest", required = false) UpdateItemRequest form,
                                          @RequestParam(required = false, name = "images") List<MultipartFile> multipartFileList) throws IOException {
        salesService.update(id,form, multipartFileList); // 상품 수정

        Item item = itemService.findById(id);
        return new UpdateSalesResponse(id, item.getName(), item.getMainImg_path());
    }


    // 상품 단건 조회
    @GetMapping("/{id}")
    public GetItemResponse findById(@PathVariable Long id) {
        Item item = itemService.findById(id);

        return new GetItemResponse(item);
    }

    // 상품 삭제
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public DeleteItemResponse delete(@PathVariable("id") Long id) {
        itemService.delete(id);
        return new DeleteItemResponse(Boolean.TRUE, "상품이 삭제되었습니다.");
    }

    // 카테고리 페이지, +이름 검색조회도 가능
    @GetMapping("/category/{code}") //CODE = "카테고리 타입"
    public Page<GetPreViewItemResponse> showCategory(
            @PathVariable("code") String code,
            @ModelAttribute("itemSearch") ItemSearch itemSearch, Pageable pageable) {

        Page<GetPreViewItemResponse> results;

        if (StringUtils.isEmpty(itemSearch.getItemName())) {
            results = itemService.categoryPageSort(code, pageable);
        } else {
            results = itemService.searchAndCategory(itemSearch, code, pageable);
        }

        return results;
    }

}
