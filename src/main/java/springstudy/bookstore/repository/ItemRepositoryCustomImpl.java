package springstudy.bookstore.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;
import springstudy.bookstore.domain.dto.MainItemDto;
import springstudy.bookstore.domain.dto.QMainItemDto;
import springstudy.bookstore.domain.dto.QUserMainItemDto;
import springstudy.bookstore.domain.dto.UserMainItemDto;
import springstudy.bookstore.domain.dto.sort.ItemSearchCondition;
import springstudy.bookstore.domain.enums.CategoryType;
import springstudy.bookstore.domain.enums.IsMainImg;

import javax.persistence.EntityManager;
import java.util.List;

import static springstudy.bookstore.domain.entity.QItem.item;
import static springstudy.bookstore.domain.entity.QItemImg.itemImg;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ItemRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<MainItemDto> searchByItemName(ItemSearchCondition condition, Pageable pageable) {

        List<MainItemDto> content = queryFactory
                .select(new QMainItemDto(
                        item.id.as("itemId"),
                        item.itemName.as("itemName"),
                        item.price,
                        item.stockQuantity,
                        itemImg.imgName,
                        item.itemType,
                        item.categoryType)
                       )
                .from(itemImg)
                .join(itemImg.item, item)
                .where(itemImg.isMainImg.eq(IsMainImg.Y))
                .where(itemNameContains(condition.getItemName()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<MainItemDto> query = queryFactory
                .select(new QMainItemDto(
                        item.id.as("itemId"),
                        item.itemName.as("itemName"),
                        item.price,
                        item.stockQuantity,
                        itemImg.imgName,
                        item.itemType,
                        item.categoryType))
                .from(itemImg)
                .join(itemImg.item, item);

        return PageableExecutionUtils.getPage(content, pageable, () -> query.fetch().size());
    }

    @Override
    public Page<MainItemDto> sortByCategoryType(String code, Pageable pageable) {
        List<MainItemDto> content = queryFactory
                .select(new QMainItemDto(
                        item.id.as("itemId"),
                        item.itemName.as("itemName"),
                        item.price,
                        item.stockQuantity,
                        itemImg.imgName,
                        item.itemType,
                        item.categoryType)
                       )
                .from(itemImg)
                .join(itemImg.item, item)
                .where(itemImg.isMainImg.eq(IsMainImg.Y))
                .where(categoryTypeContains(code))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<MainItemDto> query = queryFactory
                .select(new QMainItemDto(
                    item.id.as("itemId"),
                    item.itemName.as("itemName"),
                    item.price,
                    item.stockQuantity,
                    itemImg.imgName,
                    item.itemType,
                    item.categoryType))
                .from(itemImg)
                .join(itemImg.item, item)
                .where(categoryTypeContains(code));

        return PageableExecutionUtils.getPage(content, pageable, () -> query.fetch().size());
    }

    @Override
    public Page<MainItemDto> searchByItemNameAndCategoryType(ItemSearchCondition condition, String code, Pageable pageable) {
        List<MainItemDto> content = queryFactory
                .select(new QMainItemDto(
                        item.id.as("itemId"),
                        item.itemName.as("itemName"),
                        item.price,
                        item.stockQuantity,
                        itemImg.imgName,
                        item.itemType,
                        item.categoryType)
                       )
                .from(itemImg)
                .join(itemImg.item, item)
                .where(itemImg.isMainImg.eq(IsMainImg.Y))
                .where(categoryTypeContains(code))
                .where(itemNameContains(condition.getItemName()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<MainItemDto> query = queryFactory
                .select(new QMainItemDto(
                        item.id.as("itemId"),
                        item.itemName.as("itemName"),
                        item.price,
                        item.stockQuantity,
                        itemImg.imgName,
                        item.itemType,
                        item.categoryType))
                .from(itemImg)
                .join(itemImg.item, item)
                .where(categoryTypeContains(code));

        return PageableExecutionUtils.getPage(content, pageable, () -> query.fetch().size());
    }

    @Override
    public Page<MainItemDto> sortByItemPriceASC(Pageable pageable) {
        List<MainItemDto> content = queryFactory
                .select(new QMainItemDto(
                        item.id.as("itemId"),
                        item.itemName.as("itemName"),
                        item.price,
                        item.stockQuantity,
                        itemImg.imgName,
                        item.itemType,
                        item.categoryType)
                       )
                .from(itemImg)
                .join(itemImg.item, item)
                .where(itemImg.isMainImg.eq(IsMainImg.Y))
                .orderBy(item.price.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<MainItemDto> query = queryFactory
                .select(new QMainItemDto(
                    item.id.as("itemId"),
                    item.itemName.as("itemName"),
                    item.price,
                    item.stockQuantity,
                    itemImg.imgName,
                    item.itemType,
                    item.categoryType))
                .from(itemImg)
                .join(itemImg.item, item)
                .orderBy(item.price.asc());

        return PageableExecutionUtils.getPage(content, pageable, () -> query.fetch().size());
    }

    @Override
    public Page<MainItemDto> sortByItemPriceDESC(Pageable pageable) {
        List<MainItemDto> content = queryFactory
                .select(new QMainItemDto(
                        item.id.as("itemId"),
                        item.itemName.as("itemName"),
                        item.price,
                        item.stockQuantity,
                        itemImg.imgName,
                        item.itemType,
                        item.categoryType)
                       )
                .from(itemImg)
                .join(itemImg.item, item)
                .where(itemImg.isMainImg.eq(IsMainImg.Y))
                .orderBy(item.price.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<MainItemDto> query = queryFactory
                .select(new QMainItemDto(
                    item.id.as("itemId"),
                    item.itemName.as("itemName"),
                    item.price,
                    item.stockQuantity,
                    itemImg.imgName,
                    item.itemType,
                    item.categoryType))
                .from(itemImg)
                .join(itemImg.item, item)
                .orderBy(item.price.asc());

        return PageableExecutionUtils.getPage(content, pageable, () -> query.fetch().size());
    }

    @Override
    public List<UserMainItemDto> sortByUser() {
        List<UserMainItemDto> content = queryFactory
                .select(new QUserMainItemDto(
                        item.id.as("itemId"),
                        item.itemName.as("itemName"),
                        item.price,
                        item.stockQuantity,
                        itemImg.imgName,
                        item.itemType,
                        item.categoryType))
                .from(itemImg)
                .join(itemImg.item, item)
                .where(itemImg.isMainImg.eq(IsMainImg.Y))
                .fetch();

        return content;
    }

    private BooleanExpression itemNameContains(String itemName) {
        return StringUtils.hasText(itemName) ? item.itemName.contains(itemName) : null;
    }

    private static BooleanExpression categoryTypeContains(String code) {
        return StringUtils.hasText(code) ?  item.categoryType.eq(CategoryType.enumOf(code)) : null;
    }


}