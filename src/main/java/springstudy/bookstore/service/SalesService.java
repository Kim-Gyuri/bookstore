package springstudy.bookstore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import springstudy.bookstore.domain.dto.item.CreateItemRequest;
import springstudy.bookstore.domain.dto.item.GetUserItemResponse;
import springstudy.bookstore.domain.dto.item.UpdateItemRequest;
import springstudy.bookstore.domain.dto.sales.GetSalesResponse;
import springstudy.bookstore.domain.entity.Item;
import springstudy.bookstore.domain.entity.Sales;
import springstudy.bookstore.domain.entity.User;
import springstudy.bookstore.repository.SalesRepository;
import springstudy.bookstore.util.exception.sales.NotFoundSalesException;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SalesService {

    private final SalesRepository salesRepository;
    private final ItemService itemService;
    private final UserService userService;


    // 상품 업로드
    public Long uploadItem(User user, CreateItemRequest dto, List<MultipartFile> multipartFileList) throws IOException {
        Long itemId = itemService.saveItem(user, dto, multipartFileList);
        Item item = itemService.findById(itemId);
        user.uploadItem(item);

        return user.getSales().getId();
    }


    // 상품 조회
    // id로 조회
    // 전체 조회
    @Transactional(readOnly = true)
    public Sales findById(Long id) {
        return salesRepository.findById(id)
                .orElseThrow(() -> new NotFoundSalesException("해당 " + id + " 번호 상품이 없습니다."));
    }

    @Transactional(readOnly = true)
    public  GetSalesResponse findByUserLoginId(String id) {
        User seller = userService.findByLoginId(id);
        List<GetUserItemResponse> items = userService.findItemsByUser(id);

        return new GetSalesResponse(items, seller.income());
    }

    // 업로드한 상품 삭제
    public void delete(Long id) {
        Sales sales = findById(id);
        salesRepository.delete(sales);
    }

    // 업로드 수정
    public void update(Long itemId, UpdateItemRequest form, List<MultipartFile> multipartFileList) throws IOException {
        itemService.update(itemId,form,multipartFileList);
    }

}
