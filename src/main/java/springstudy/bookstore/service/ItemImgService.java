package springstudy.bookstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import springstudy.bookstore.domain.dto.FileInfoDto;
import springstudy.bookstore.domain.dto.ItemInfoDto;
import springstudy.bookstore.domain.entity.ItemImg;
import springstudy.bookstore.repository.ItemImgRepository;

import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemImgService {

    private final FileService fileService;
    private final ItemImgRepository imgRepository;

    public Long saveItemImg(ItemInfoDto itemInfo, MultipartFile multipartFile) throws IOException {
        FileInfoDto fileInfo = fileService.storeFile(multipartFile);

        ItemImg itemImgEntity = ItemImg.imgBuilder()
                .originImgName(fileInfo.getOriginImgName())
                .imgName(fileInfo.getImgName())
                .savePath(fileInfo.getSavePath())
                .isMainImg(itemInfo.getYN())
                .item(itemInfo.getItem())
                .build();

        ItemImg saved = imgRepository.save(itemImgEntity);
        return saved.getId();
    }

    public ItemImg findByImgName(String imgName) {
        return imgRepository.findByImgName(imgName);
    }

    public ItemImg findByImgId(long id) {
        return imgRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 이미지가 없습니다."));
    }

    public void delete(ItemImg imgEntity) {
        imgRepository.delete(imgEntity);
    }

}
