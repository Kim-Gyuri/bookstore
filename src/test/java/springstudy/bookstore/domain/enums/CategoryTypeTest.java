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
        // given : "MUSIC" 카테고리 타입코드로 조회했을 때
        String categoryCode = selectCategoryCode();
        CategoryType findType = CategoryType.enumOf(categoryCode);

        // then : 카테고리 이름이 "음반"이 맞는지 확인한다.
        assertThat(findType.getCategoryName().equals(CategoryType.MUSIC.getCategoryName()));
        assertThat(findType.getCode().equals(CategoryType.MUSIC.getCode()));
    }

    private String selectCategoryCode() {
        return "MUSIC";
    }

}