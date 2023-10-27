package springstudy.bookstore.domain.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ItemSellStatus {
    SELL("SELL", "판매중"),
    SOLD_OUT("SOLD_OUT", "품절");

    private String code;
    private String statusInfo;

    ItemSellStatus(String code, String statusInfo) {
        this.code = code;
        this.statusInfo = statusInfo;
    }
    public static ItemSellStatus enumOf(String code) {
        //ItemSellStatus의 Enum 상수들을 순회하며
        return Arrays.stream(ItemSellStatus.values())
                // 같은 code 갖고 있는게 있는지 확인한다.
                .filter(t->t.getCode().equals(code))
                .findAny().orElse(null);
    }
}
