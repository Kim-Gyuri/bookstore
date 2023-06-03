package springstudy.bookstore.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springstudy.bookstore.domain.enums.CategoryType;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemCategory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long category_id;

    private CategoryType categoryType;

    @Builder
    public ItemCategory(CategoryType type){
        this.categoryType = type;
    }

}
