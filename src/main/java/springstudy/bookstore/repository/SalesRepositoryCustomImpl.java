package springstudy.bookstore.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import springstudy.bookstore.controller.api.dto.sort.ItemSearch;
import springstudy.bookstore.domain.dto.item.GetPreViewItemResponse;
import springstudy.bookstore.domain.dto.item.GetUserItemResponse;
import springstudy.bookstore.domain.dto.item.QGetPreViewItemResponse;
import springstudy.bookstore.domain.dto.item.QGetUserItemResponse;
import springstudy.bookstore.domain.dto.sales.QSalesChat;
import springstudy.bookstore.domain.dto.sales.SalesChat;
import springstudy.bookstore.domain.enums.IsMainImg;
import springstudy.bookstore.domain.enums.ItemSellStatus;
import springstudy.bookstore.domain.enums.OrderStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static springstudy.bookstore.domain.entity.QItem.item;
import static springstudy.bookstore.domain.entity.QItemImg.itemImg;
import static springstudy.bookstore.domain.entity.QOrderItem.orderItem;
import static springstudy.bookstore.domain.entity.QSales.sales;

public class SalesRepositoryCustomImpl implements SalesRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public SalesRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<GetUserItemResponse> searchItemByUserAndItemStatus(String uploaderId, ItemSearch itemSearch, Pageable pageable) {

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
                .where(item.status.eq(itemStatusContains(itemSearch)))
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
                .where(item.status.eq(itemStatusContains(itemSearch)));

        return PageableExecutionUtils.getPage(content, pageable, () -> query.fetch().size());
    }


    @Override
    public Page<GetUserItemResponse> searchOrderByUserAndItemName(String uploaderId, ItemSearch itemSearch, Pageable pageable) {

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
                .from(sales)
                .join(sales.itemList, item)
                .join(item.imgList, itemImg)
                .where(itemImg.isMainImg.eq(IsMainImg.Y).and(item.sellerId.eq(uploaderId)))
                .where(item.status.eq(itemStatusContains(itemSearch)))
              //  .where(sales.orderItemList.orderStatus.eq(itemSearch.getOrderStatus())
                .fetch();

        JPAQuery<GetUserItemResponse> query = queryFactory
                .select(new QGetUserItemResponse(
                        item.id.as("itemId"),
                        item.name.as("itemName"),
                        item.price,
                        item.stockQuantity,
                        itemImg.imgName,
                        itemImg.savePath,
                        item.itemType,
                        item.categoryType))
                .from(sales)
                .join(sales.itemList, item)
                .join(item.imgList, itemImg)
                .where(itemImg.isMainImg.eq(IsMainImg.Y).and(item.sellerId.eq(uploaderId)))
                .where(item.status.eq(itemStatusContains(itemSearch))
                );

        return PageableExecutionUtils.getPage(content, pageable, () -> query.fetch().size());
    }


    @Override
    public List<SalesChat> findByOrderDate(String uploaderId) {
        List<SalesChat> result = queryFactory
                .select(new QSalesChat(
                        orderItem.orderDate,
                        orderItem.orderPrice.sum()))
                .from(orderItem)
                .where(orderItem.item.sellerId.eq(uploaderId))
                .where(orderItem.orderStatus.eq(OrderStatus.ORDER))
                .groupBy(orderItem.orderDate.month())
                .fetch();

        // 모든 월을 포함하는 빈 SalesChat 리스트를 만듭니다.
        List<SalesChat> fullResult = createEmptySalesChatList();

        // 결과를 루프하며 빈 리스트에 결과를 채웁니다.
        for (SalesChat salesChat : result) {
            int month = salesChat.getOrderDate().getMonthValue();
            fullResult.set(month - 1, salesChat); // 월은 1부터 시작하므로 인덱스에 맞게 설정
        }
        return fullResult;
    }

    private List<SalesChat> createEmptySalesChatList() {
        LocalDate current = LocalDate.now();
        return IntStream.rangeClosed(1, 12)
                .mapToObj(month -> new SalesChat(LocalDate.of(current.getYear(), month, 1), 0))
                .collect(Collectors.toList());
    }


    // 판매 상태 조건 추가
    private ItemSellStatus itemStatusContains(ItemSearch itemSearch) {
        String code = (itemSearch.getItemStatus() != null) ? itemSearch.getItemStatus().getCode() : ItemSellStatus.SELL.getCode();
        return ItemSellStatus.enumOf(code);
    }
}
