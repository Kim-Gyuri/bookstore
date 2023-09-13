package springstudy.bookstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import springstudy.bookstore.domain.dto.item.GetPreViewItemResponse;
import springstudy.bookstore.domain.dto.item.GetUserItemResponse;

import java.util.List;

public interface ItemRepositoryCustom {
    // 상품 이름으로 상품 조회
    Page<GetPreViewItemResponse> searchByItemName(String itemName, Pageable pageable);

    // 상품 카테고리별 상품 조회
    Page<GetPreViewItemResponse> sortByCategoryType(String code, Pageable pageable);

    // 상품 이름 검색 기능 + 카테고리별 정렬
    Page<GetPreViewItemResponse> searchByItemNameAndCategoryType(String itemName, String code, Pageable pageable);

    // 상품 낮은 가격별 정렬
    Page<GetPreViewItemResponse> sortByItemPriceASC(Pageable pageable);

    // 상품 높은 가격별 정렬
    Page<GetPreViewItemResponse> sortByItemPriceDESC(Pageable pageable);

    // 회원별 등록한 상품 조회
    List<GetUserItemResponse> sortByUser(String uploaderId);

}
