package springstudy.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springstudy.bookstore.domain.entity.Item;
import springstudy.bookstore.domain.enums.CategoryType;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {

    Item findByName(String itemName); // 상품 이름으로 상품 조회
    List<Item> findAllBySellerId(String userId); // 판매자 로그인 ID로 판매상품 조회

    boolean existsByNameAndCategoryType(String itemName, CategoryType categoryType); // 현재 해당 이름/카테고리 타입인 상품이 존재하는지 확인
}
