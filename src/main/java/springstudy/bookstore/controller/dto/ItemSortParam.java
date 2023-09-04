package springstudy.bookstore.controller.dto;


import lombok.Getter;

@Getter
public enum ItemSortParam {
    DESC("DESC", "낮은가격 순"),
    ASC("ASC", "높은가격 순");
    private String code;
    private String displayName;

    ItemSortParam(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
}