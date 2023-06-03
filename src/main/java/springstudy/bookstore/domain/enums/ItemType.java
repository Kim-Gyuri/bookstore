package springstudy.bookstore.domain.enums;

import lombok.Getter;

@Getter
public enum ItemType {
    HIGHEST("HIGHEST", "최상", "새상품 또는 새상품과 동일한 상태이나 개봉한 상태이며 부속품이 일부 없을 수 있다."),
    BEST("BEST", "상", "약간의 사용감은 있으나 깨끗한 상태로, 희미한 변색이나 작은 얼룩이 있다."),
    LOWER("LOWER", "중", "사용감이 많으며 헌 느낌이 난다. 전체적인 변색,해짐,낙서,얼룩 등등 있다.");

    private String code;
    private String itemType;
    private String statusInfo;

    ItemType(String code, String itemType, String statusInfo) {
        this.code = code;
        this.itemType = itemType;
        this.statusInfo = statusInfo;
    }

}
