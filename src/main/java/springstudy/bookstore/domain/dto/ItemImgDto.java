package springstudy.bookstore.domain.dto;

import lombok.Data;
import org.modelmapper.ModelMapper;
import springstudy.bookstore.domain.entity.ItemImg;
import springstudy.bookstore.domain.enums.IsMainImg;

@Data
public class ItemImgDto {

    private Long id;
    private String imgName;
    private String originImgName;
    private String savePath;
    private IsMainImg YN;

    private static ModelMapper modelMapper = new ModelMapper();

    public static ItemImgDto of(ItemImg itemImg) {
        return modelMapper.map(itemImg, ItemImgDto.class);
    }
}
