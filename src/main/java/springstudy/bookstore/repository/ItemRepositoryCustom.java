package springstudy.bookstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import springstudy.bookstore.domain.dto.MainItemDto;
import springstudy.bookstore.domain.dto.UserMainItemDto;
import springstudy.bookstore.domain.dto.sort.ItemSearchCondition;

import java.util.List;

public interface ItemRepositoryCustom {

    Page<MainItemDto> searchByItemName(ItemSearchCondition condition, Pageable pageable);
    Page<MainItemDto> sortByCategoryType(String code, Pageable pageable);
    Page<MainItemDto> searchByItemNameAndSortByItemPrice(ItemSearchCondition condition, String code, Pageable pageable);
    Page<MainItemDto> sortByItemPriceASC(Pageable pageable);
    Page<MainItemDto> sortByItemPriceDESC(Pageable pageable);
    List<UserMainItemDto> sortByUser();

}
