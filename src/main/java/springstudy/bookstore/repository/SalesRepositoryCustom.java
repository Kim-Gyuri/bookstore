package springstudy.bookstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import springstudy.bookstore.controller.api.dto.sort.ItemSearch;
import springstudy.bookstore.domain.dto.item.GetUserItemResponse;
import springstudy.bookstore.domain.dto.sales.SalesChat;

import java.util.List;

public interface SalesRepositoryCustom {

    // 회원별 등록한 상품 주문 상태 검색
    Page<GetUserItemResponse> searchItemByUserAndItemStatus(String uploaderId, ItemSearch itemSearch, Pageable pageable);
    Page<GetUserItemResponse> searchOrderByUserAndItemName(String uploaderId, ItemSearch itemSearch, Pageable pageable);

    List<SalesChat> findByOrderDate(String uploaderId);

}
