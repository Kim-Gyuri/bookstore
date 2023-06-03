package springstudy.bookstore.domain.dto.sort;

import java.util.Arrays;
import java.util.List;

public class ItemSortParamsCreate {

    private static final List<SortParams> sortParams = Arrays.asList(
            new SortParams("desc", "낮은가격 순"),
            new SortParams("asc", "높은가격 순"));
           // new SortParams("itemName,ASC", "이름순"));

    public static List<SortParams> getInstance() {
        return sortParams;
    }

    private ItemSortParamsCreate() {}

}