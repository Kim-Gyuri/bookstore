package springstudy.bookstore.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;
import springstudy.bookstore.domain.dto.item.GetPreViewItemResponse;
import springstudy.bookstore.domain.dto.item.GetUserItemResponse;
import springstudy.bookstore.domain.dto.item.QGetPreViewItemResponse;
import springstudy.bookstore.domain.dto.item.QGetUserItemResponse;
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
    public Page<GetPreViewItemResponse> searchByItemName(String itemName, Pageable pageable) {

        List<GetPreViewItemResponse> content = queryFactory
                .select(new QGetPreViewItemResponse(
                        item.id.as("itemId"),
                        item.name.as("itemName"),
                        item.price,
                        item.stockQuantity,
                        itemImg.imgName,
                        itemImg.savePath,
                        item.itemType,
                        item.categoryType)
                       )
                .from(itemImg)
                .join(itemImg.item, item)
                .where(itemImg.isMainImg.eq(IsMainImg.Y))
                .where(itemNameContains(itemName))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<GetPreViewItemResponse> query = queryFactory
                .select(new QGetPreViewItemResponse(
                        item.id.as("itemId"),
                        item.name.as("itemName"),
                        item.price,
                        item.stockQuantity,
                        itemImg.imgName,
                        itemImg.savePath,
                        item.itemType,
                        item.categoryType))
                .from(itemImg)
                .join(itemImg.item, item);

        return PageableExecutionUtils.getPage(content, pageable, () -> query.fetch().size());
    }

    @Override
    public Page<GetPreViewItemResponse> sortByCategoryType(String code, Pageable pageable) {
        List<GetPreViewItemResponse> content = queryFactory
                .select(new QGetPreViewItemResponse(
                        item.id.as("itemId"),
                        item.name.as("itemName"),
                        item.price,
                        item.stockQuantity,
                        itemImg.imgName,
                        itemImg.savePath,
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

        JPAQuery<GetPreViewItemResponse> query = queryFactory
                .select(new QGetPreViewItemResponse(
                    item.id.as("itemId"),
                    item.name.as("itemName"),
                    item.price,
                    item.stockQuantity,
                    itemImg.imgName,
                    itemImg.savePath,
                    item.itemType,
                    item.categoryType))
                .from(itemImg)
                .join(itemImg.item, item)
                .where(categoryTypeContains(code));

        return PageableExecutionUtils.getPage(content, pageable, () -> query.fetch().size());
    }

    @Override
    public Page<GetPreViewItemResponse> searchByItemNameAndCategoryType(String itemName, String code, Pageable pageable) {
        List<GetPreViewItemResponse> content = queryFactory
                .select(new QGetPreViewItemResponse(
                        item.id.as("itemId"),
                        item.name.as("itemName"),
                        item.price,
                        item.stockQuantity,
                        itemImg.imgName,
                        itemImg.savePath,
                        item.itemType,
                        item.categoryType)
                       )
                .from(itemImg)
                .join(itemImg.item, item)
                .where(itemImg.isMainImg.eq(IsMainImg.Y))
                .where(categoryTypeContains(code))
                .where(itemNameContains(itemName))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<GetPreViewItemResponse> query = queryFactory
                .select(new QGetPreViewItemResponse(
                        item.id.as("itemId"),
                        item.name.as("itemName"),
                        item.price,
                        item.stockQuantity,
                        itemImg.imgName,
                        itemImg.savePath,
                        item.itemType,
                        item.categoryType))
                .from(itemImg)
                .join(itemImg.item, item)
                .where(categoryTypeContains(code));

        return PageableExecutionUtils.getPage(content, pageable, () -> query.fetch().size());
    }

    @Override
    public Page<GetPreViewItemResponse> sortByItemPriceASC(Pageable pageable) {
        List<GetPreViewItemResponse> content = queryFactory
                .select(new QGetPreViewItemResponse(
                        item.id.as("itemId"),
                        item.name.as("itemName"),
                        item.price,
                        item.stockQuantity,
                        itemImg.imgName,
                        itemImg.savePath,
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

        JPAQuery<GetPreViewItemResponse> query = queryFactory
                .select(new QGetPreViewItemResponse(
                    item.id.as("itemId"),
                    item.name.as("itemName"),
                    item.price,
                    item.stockQuantity,
                    itemImg.imgName,
                    itemImg.savePath,
                    item.itemType,
                    item.categoryType))
                .from(itemImg)
                .join(itemImg.item, item)
                .orderBy(item.price.asc());

        return PageableExecutionUtils.getPage(content, pageable, () -> query.fetch().size());
    }

    @Override
    public Page<GetPreViewItemResponse> sortByItemPriceDESC(Pageable pageable) {
        List<GetPreViewItemResponse> content = queryFactory
                .select(new QGetPreViewItemResponse(
                        item.id.as("itemId"),
                        item.name.as("itemName"),
                        item.price,
                        item.stockQuantity,
                        itemImg.imgName,
                        itemImg.savePath,
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

        JPAQuery<GetPreViewItemResponse> query = queryFactory
                .select(new QGetPreViewItemResponse(
                    item.id.as("itemId"),
                    item.name.as("itemName"),
                    item.price,
                    item.stockQuantity,
                    itemImg.imgName,
                    itemImg.savePath,
                    item.itemType,
                    item.categoryType))
                .from(itemImg)
                .join(itemImg.item, item)
                .orderBy(item.price.asc());

        return PageableExecutionUtils.getPage(content, pageable, () -> query.fetch().size());
    }

    @Override
    public List<GetUserItemResponse> sortByUser(String uploaderId) {
        List<GetUserItemResponse> content = queryFactory
                .select(new QGetUserItemResponse(
                        item.id.as("itemId"),
                        item.name.as("itemName"),
                        item.price,
                        item.stockQuantity,
                        itemImg.imgName,
                        itemImg.savePath,
                        item.itemType,
                        item.categoryType))
                .from(itemImg)
                .join(itemImg.item, item)
                .where(itemImg.isMainImg.eq(IsMainImg.Y).and(item.sellerId.eq(uploaderId)))
                .fetch();

        return content;
    }



    private BooleanExpression itemNameContains(String itemName) {
        return StringUtils.hasText(itemName) ? item.name.contains(itemName) : null;
    }

    private static BooleanExpression categoryTypeContains(String code) {
        return StringUtils.hasText(code) ?  item.categoryType.eq(CategoryType.enumOf(code)) : null;
    }


}