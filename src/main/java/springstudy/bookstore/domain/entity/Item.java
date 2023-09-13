package springstudy.bookstore.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springstudy.bookstore.domain.enums.CategoryType;
import springstudy.bookstore.domain.enums.ItemSellStatus;
import springstudy.bookstore.domain.enums.ItemType;
import springstudy.bookstore.util.exception.item.NotEnoughStockException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static springstudy.bookstore.util.constant.Constants.THUMBNAIL_INDEX;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String sellerId; // 상품 등록자; 판매자의 ID
    private String name;
    private Integer price;
    private Integer stockQuantity;

    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    @Enumerated(EnumType.STRING)
    private CategoryType categoryType;

    @Enumerated(EnumType.STRING)
    private ItemSellStatus status;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<ItemImg> imgList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_id")
    private Sales sales;

    // H2 DB 테스트 할 때, 입력 데이터를 만들기 위해
    @Builder(builderMethodName = "initItemBuilder")
    public Item(String sellerId, String itemName, Integer price, Integer stockQuantity, ItemType itemType, CategoryType categoryType) {
        this.sellerId = sellerId;
        this.name = itemName;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.itemType = itemType;
        this.categoryType = categoryType;
        this.status = ItemSellStatus.SELL;
    }

    public void sellerInfo(String userId) {
        this.sellerId = userId;
    }

    public void removeStock(Integer quantity) {
        int restQuantity = this.stockQuantity - quantity;
        if (restQuantity < 0) {
            throw new NotEnoughStockException("더 이상 남아있는 재고가 없습니다.");
        }
        this.stockQuantity = restQuantity;
    }

    public void checkStatus() {
        Integer restQuantity = this.stockQuantity;
        if (restQuantity < 0) {
            throw new NotEnoughStockException("재고가 부족합니다.");
        }
        this.status = ItemSellStatus.SOLD_OUT;
    }

    public void cancelCart(Integer quantity) {
        int restQuantity = this.stockQuantity + quantity;
        this.stockQuantity = restQuantity;
    }


    public void update(String itemName, Integer price, Integer stockQuantity) {
        this.name = itemName;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public void addMoreItemImg(ItemImg itemImg) {
        this.imgList.add(itemImg);
    }

    public void deleteItemImg(ItemImg itemImg) {
        this.imgList.remove(itemImg);
    }


    public String getMainImg_path() {
        return imgList.get(THUMBNAIL_INDEX).getSavePath();
    }
    @Override
    public String toString() {
        return "Item Info {" + "id=" + id + ", name=" + name + ", price=" + price + ", stockQuantity =" + stockQuantity + ", itemType =" + itemType + ", categoryType =" + categoryType + ", status=" + status + '}';
    }

}
