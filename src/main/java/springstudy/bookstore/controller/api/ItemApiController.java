package springstudy.bookstore.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springstudy.bookstore.controller.api.dto.item.DeleteItemResponse;
import springstudy.bookstore.controller.api.dto.item.GetItemResponse;
import springstudy.bookstore.controller.api.dto.sales.CreateSalesResponse;
import springstudy.bookstore.controller.api.dto.sales.UpdateSalesResponse;
import springstudy.bookstore.domain.dto.item.CreateItemRequest;
import springstudy.bookstore.domain.dto.item.GetPreViewItemResponse;
import springstudy.bookstore.domain.dto.item.UpdateItemRequest;
import springstudy.bookstore.domain.dto.sort.ItemSearchCondition;
import springstudy.bookstore.domain.entity.Item;
import springstudy.bookstore.domain.entity.User;
import springstudy.bookstore.service.ItemService;
import springstudy.bookstore.service.SalesService;
import springstudy.bookstore.service.UserService;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/items")
public class ItemApiController {

    private final SalesService salesService;
    private final UserService userService;
    private final ItemService itemService;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CreateSalesResponse uploadItem(String loginId, CreateItemRequest dto, List<MultipartFile> files) throws IOException {
        User seller = userService.findByLoginId(loginId);
        Long id = salesService.uploadItem(seller, dto, files);

        return new CreateSalesResponse(id);
    }
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public UpdateSalesResponse updateItem(@PathVariable("id") Long id, UpdateItemRequest form, List<MultipartFile> multipartFileList) throws IOException {
        salesService.update(id,form, multipartFileList);
        Item item = itemService.findById(id);
        return new UpdateSalesResponse(id, item.getItemName(), item.getMainImg_path());
    }


    @GetMapping("/{id}")
    public GetItemResponse findById(@PathVariable Long id) {
        Item item = itemService.findById(id);

        return new GetItemResponse(item);
    }

    /**
     * 조건별 조회
     * 1. 카테고리별 조회
     * 2. 등급별 조회
     * 3. 가격순 정렬
     */

    // 삭제
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public DeleteItemResponse delete(@PathVariable("id") Long id) {
        itemService.delete(id);
        return new DeleteItemResponse(Boolean.TRUE, "상품이 삭제되었습니다.");
    }

    // 카테고리 페이지, +이름 검색조회도 가능
    @GetMapping("/category/{code}") //CODE = "카테고리 타입"
    public Page<GetPreViewItemResponse> showCategory(Model model , Pageable pageable,
                               @PathVariable("code") String code, ItemSearchCondition condition) {

        Page<GetPreViewItemResponse> results;
        //PageDto pageDto;

        if (condition.getItemName() == null) {
            results = itemService.categoryPageSort(code, pageable);
        } else {
            results = itemService.searchAndCategory(condition, code, pageable);
        }
        return results;
    }

    // 모든 상품 조회, + 추가로 상품 가격순 정렬 필터 가능, 추가로 이름검색 조회
    @GetMapping
    public Page<GetPreViewItemResponse> findAll(Pageable pageable,
                       @RequestParam(required = false, name = "code") String code,
                       ItemSearchCondition condition) {

        Page<GetPreViewItemResponse> results;

        if (code == null) {
            results = itemService.searchPageSort(condition, pageable);
        } else {
            results = itemService.itemPriceSort(code, pageable);
        }
        return results;
    }

}
