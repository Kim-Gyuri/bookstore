package springstudy.bookstore.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springstudy.bookstore.domain.enums.IsMainImg;

import jakarta.persistence.*;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemImg {
    @Id @GeneratedValue
    @Column(name = "item_img_id")
    private Long id;

    private String originImgName;
    private String imgName;
    private String savePath;
    private IsMainImg isMainImg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Builder(builderMethodName = "imgBuilder")
    public ItemImg(String originImgName, String imgName, String savePath, IsMainImg isMainImg, Item item) {
        this.originImgName = originImgName;
        this.imgName = imgName;
        this.savePath = savePath;
        this.isMainImg = isMainImg;
       this.item = item;
    }

    @Override
    public String toString() {
        return "ItemImg Info {" + " ImgName =" + originImgName + ", storeFileName =" + imgName + ", savePath =" + savePath + '}';
    }
    
}
