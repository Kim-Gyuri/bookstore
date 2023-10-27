package springstudy.bookstore.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import springstudy.bookstore.domain.dto.orderItem.GetOrderItemResponse;
import springstudy.bookstore.domain.dto.orderItem.QGetOrderItemResponse;
import springstudy.bookstore.domain.enums.IsMainImg;
import springstudy.bookstore.domain.enums.OrderStatus;

import java.util.List;

import static springstudy.bookstore.domain.entity.QItemImg.itemImg;
import static springstudy.bookstore.domain.entity.QOrderItem.orderItem;

public class OrderItemRepositoryCustomImpl implements OrderItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public OrderItemRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<GetOrderItemResponse> findAllByCart_id(Long id) {

        return queryFactory
                .select(new QGetOrderItemResponse(
                        orderItem.id,
                        orderItem.item.id.as("itemId"),
                        orderItem.item.name,
                        itemImg.savePath,
                        orderItem.count,
                        orderItem.orderPrice,
                        orderItem.orderStatus)
                       )
                .from(orderItem)
                .leftJoin(itemImg)
                .on(orderItem.item.id.eq(itemImg.item.id))
                .where(orderItem.cart.id.eq(id))
                .fetch();
    }
    @Override
    public List<GetOrderItemResponse> findAllBySellerId(String id) {

        return queryFactory
                .select(new QGetOrderItemResponse(
                        orderItem.id,
                        orderItem.item.id.as("itemId"),
                        orderItem.item.name,
                        itemImg.savePath,
                        orderItem.count,
                        orderItem.orderPrice,
                        orderItem.orderStatus)
                       )
                .from(orderItem)
                .leftJoin(itemImg)
                .on(orderItem.item.id.eq(itemImg.item.id))
                .where(orderItem.item.sellerId.eq(id))
                .fetch();
    }



    @Override
    public Page<GetOrderItemResponse> findAllBySellerId_paging(String id, OrderStatus orderStatus, Pageable pageable) {

        List<GetOrderItemResponse> content = queryFactory
                .select(new QGetOrderItemResponse(
                        orderItem.id,
                        orderItem.item.id.as("itemId"),
                        orderItem.item.name,
                        itemImg.savePath,
                        orderItem.count,
                        orderItem.orderPrice,
                        orderItem.orderStatus)
                       )
                .from(orderItem)
                .leftJoin(itemImg)
                .on(orderItem.item.id.eq(itemImg.item.id))
                .where(orderItem.item.sellerId.eq(id))
                .where(itemImg.isMainImg.eq(IsMainImg.Y))
                .where(extractOrderStatusExpression(orderStatus))
                .fetch();

        JPAQuery<GetOrderItemResponse> query = queryFactory
                .select(new QGetOrderItemResponse(
                        orderItem.id,
                        orderItem.item.id.as("itemId"),
                        orderItem.item.name,
                        itemImg.savePath,
                        orderItem.count,
                        orderItem.orderPrice,
                        orderItem.orderStatus)
                       )
                .from(orderItem)
                .leftJoin(itemImg)
                .on(orderItem.item.id.eq(itemImg.item.id))
                .where(orderItem.item.sellerId.eq(id))
                .where(extractOrderStatusExpression(orderStatus));


        return PageableExecutionUtils.getPage(content, pageable, () -> query.fetch().size());
    }

    private BooleanExpression extractOrderStatusExpression(OrderStatus orderStatus) {
        if (orderStatus != null) {
            return orderItem.orderStatus.eq(orderStatus);
        }
        return null;
    }

}
