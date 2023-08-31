package springstudy.bookstore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import springstudy.bookstore.domain.dto.ItemFormDto;
import springstudy.bookstore.domain.dto.ItemUpdateForm;
import springstudy.bookstore.domain.dto.SalesFormDto;
import springstudy.bookstore.domain.entity.Item;
import springstudy.bookstore.domain.entity.Sales;
import springstudy.bookstore.domain.entity.User;
import springstudy.bookstore.repository.SalesRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SalesService {

    private final SalesRepository salesRepository;
    private final ItemService itemService;
    private final UserService userService;
    private final CartService cartService;

    // 상품 업로드
    public void uploadItem(User user,ItemFormDto itemFormDto, List<MultipartFile> multipartFileList) throws IOException {
        Long itemId = itemService.saveItem_test(itemFormDto, multipartFileList);
        Item item = itemService.findById(itemId);
        user.uploadItem(item);
    }


    // 상품 조회
    // id로 조회
    // 전체 조회
    @Transactional(readOnly = true)
    public Sales findById(Long id) {
        return salesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 " + id + " 번호 상품이 없습니다."));
    }

    @Transactional(readOnly = true)
    public List<SalesFormDto> getItemDetail(Long id) {
        Sales sales = findById(id);

        List<SalesFormDto> list = new ArrayList<>();
        for (Item item : sales.getItemList()) {
            list.add(itemService.getItemDetail_test(item.getId()));
        }
        return list;
    }

    // 업로드한 상품 삭제
    public void delete(Long id) {
        Sales sales = salesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다."));
        salesRepository.delete(sales);
    }

    // 업로드 수정
    public void update(Long itemId, ItemUpdateForm form, List<MultipartFile> multipartFileList) throws IOException {
        itemService.update(itemId,form,multipartFileList);
    }

}
