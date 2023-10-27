package springstudy.bookstore.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QItem is a Querydsl query type for Item
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItem extends EntityPathBase<Item> {

    private static final long serialVersionUID = 1205678876L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QItem item = new QItem("item");

    public final EnumPath<springstudy.bookstore.domain.enums.CategoryType> categoryType = createEnum("categoryType", springstudy.bookstore.domain.enums.CategoryType.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<ItemImg, QItemImg> imgList = this.<ItemImg, QItemImg>createList("imgList", ItemImg.class, QItemImg.class, PathInits.DIRECT2);

    public final EnumPath<springstudy.bookstore.domain.enums.ItemType> itemType = createEnum("itemType", springstudy.bookstore.domain.enums.ItemType.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final QSales sales;

    public final StringPath sellerId = createString("sellerId");

    public final EnumPath<springstudy.bookstore.domain.enums.ItemSellStatus> status = createEnum("status", springstudy.bookstore.domain.enums.ItemSellStatus.class);

    public final NumberPath<Integer> stockQuantity = createNumber("stockQuantity", Integer.class);

    public QItem(String variable) {
        this(Item.class, forVariable(variable), INITS);
    }

    public QItem(Path<? extends Item> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QItem(PathMetadata metadata, PathInits inits) {
        this(Item.class, metadata, inits);
    }

    public QItem(Class<? extends Item> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.sales = inits.isInitialized("sales") ? new QSales(forProperty("sales")) : null;
    }

}

