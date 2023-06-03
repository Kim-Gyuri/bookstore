package springstudy.bookstore.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springstudy.bookstore.domain.enums.CategoryType;
import springstudy.bookstore.domain.enums.ItemSellStatus;
import springstudy.bookstore.domain.enums.ItemType;
import springstudy.bookstore.util.exception.NotEnoughStockException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String itemName;
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

    @Builder(builderMethodName ="itemBuilder")
    public Item(String itemName, Integer price, Integer stockQuantity, ItemType itemType, CategoryType categoryType, ItemSellStatus status) {
        this.itemName = itemName;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.itemType = itemType;
        this.categoryType = categoryType;
        this.status = status;
    }

    @Builder(builderMethodName = "initItemBuilder")
    public Item(User user, String itemName, Integer price, Integer stockQuantity, ItemType itemType, CategoryType categoryType, ItemSellStatus status) {
        this.user = user;
        this.itemName = itemName;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.itemType = itemType;
        this.categoryType = categoryType;
        this.status = status;
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

    public void setUpUser(User user) {
        this.user = user;
    }

    public void update(String itemName, Integer price, Integer stockQuantity) {
        this.itemName = itemName;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public void addMoreItemImg(ItemImg itemImg) {
        this.imgList.add(itemImg);
    }

    @Override
    public String toString() {
        return "Item Info {" + "id=" + id + ", name=" + itemName + ", price=" + price + ", stockQuantity =" + stockQuantity + ", itemType =" + itemType + ", categoryType =" + categoryType + ", status=" + status + '}';
    }

}
