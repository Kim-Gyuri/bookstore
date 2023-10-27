package springstudy.bookstore.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSales is a Querydsl query type for Sales
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSales extends EntityPathBase<Sales> {

    private static final long serialVersionUID = -1269984733L;

    public static final QSales sales = new QSales("sales");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<Item, QItem> itemList = this.<Item, QItem>createList("itemList", Item.class, QItem.class, PathInits.DIRECT2);

    public final ListPath<OrderItem, QOrderItem> orderItemList = this.<OrderItem, QOrderItem>createList("orderItemList", OrderItem.class, QOrderItem.class, PathInits.DIRECT2);

    public final NumberPath<Integer> totalRevenue = createNumber("totalRevenue", Integer.class);

    public QSales(String variable) {
        super(Sales.class, forVariable(variable));
    }

    public QSales(Path<? extends Sales> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSales(PathMetadata metadata) {
        super(Sales.class, metadata);
    }

}

