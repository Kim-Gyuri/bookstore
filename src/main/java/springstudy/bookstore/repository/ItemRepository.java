package springstudy.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springstudy.bookstore.domain.entity.Item;
import springstudy.bookstore.domain.enums.CategoryType;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {

    Item findByItemName(String itemName);
    List<Item> findAllBySellerId(String userId); // 판매자 ID로 판매상품 조회

    boolean existsByItemNameAndCategoryType(String itemName, CategoryType categoryType);
}
