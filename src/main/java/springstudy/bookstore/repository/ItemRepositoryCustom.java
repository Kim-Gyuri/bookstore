package springstudy.bookstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import springstudy.bookstore.domain.dto.item.GetPreViewItemResponse;
import springstudy.bookstore.domain.dto.item.GetUserItemResponse;
import springstudy.bookstore.domain.dto.sort.ItemSearchCondition;

import java.util.List;

public interface ItemRepositoryCustom {

    Page<GetPreViewItemResponse> searchByItemName(ItemSearchCondition condition, Pageable pageable);
    Page<GetPreViewItemResponse> sortByCategoryType(String code, Pageable pageable);
    Page<GetPreViewItemResponse> searchByItemNameAndCategoryType(ItemSearchCondition condition, String code, Pageable pageable);
    Page<GetPreViewItemResponse> sortByItemPriceASC(Pageable pageable);
    Page<GetPreViewItemResponse> sortByItemPriceDESC(Pageable pageable);
    List<GetUserItemResponse> sortByUser(String uploaderId);

}
