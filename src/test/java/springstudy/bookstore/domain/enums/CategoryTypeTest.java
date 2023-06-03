package springstudy.bookstore.domain.enums;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CategoryTypeTest {

    @Test
    public void CategoryType에게_직접_카테고리종류_물어보기() {
        String categoryCode = selectCategoryCode();
        CategoryType findType = CategoryType.enumOf(categoryCode);

       assertThat(findType.getCategoryName().equals(CategoryType.MUSIC.getCategoryName()));
       assertThat(findType.getTypeCode().equals(CategoryType.MUSIC.getTypeCode()));
    }

    private String selectCategoryCode() {
        return "MUSIC";
    }

}