package springstudy.bookstore.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import springstudy.bookstore.domain.dto.orderItem.GetOrderItemResponse;
import springstudy.bookstore.domain.dto.orderItem.QGetOrderItemResponse;

import javax.persistence.EntityManager;
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
                        orderItem.id.as("orderItemId"),
                        orderItem.item.name,
                        itemImg.savePath,
                        orderItem.count,
                        orderItem.orderPrice)
                       )
                .from(orderItem)
                .leftJoin(itemImg)
                .on(orderItem.item.id.eq(itemImg.item.id))
                .where(orderItem.cart.id.eq(id))
                .fetch();
    }

}
