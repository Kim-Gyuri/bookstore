package springstudy.bookstore.domain.dto.file;

import lombok.Data;

@Data
public class CreateFileResponse {

    private String imgName;
    private String originImgName;
    private String savePath;

    public void updateItemImg(String originImgName, String imgName, String savePath) {
        this.originImgName = originImgName;
        this.imgName = imgName;
        this.savePath = savePath;
    }

}
