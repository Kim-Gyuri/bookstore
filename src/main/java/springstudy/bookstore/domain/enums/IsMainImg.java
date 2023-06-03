package springstudy.bookstore.domain.enums;

public enum IsMainImg {
    Y("YES", "대표이미지"),
    N("NO", "세부 이미지");

    private String YN;
    private String type;

    IsMainImg(String YN, String type) {
        this.YN = YN;
        this.type = type;
    }


}
