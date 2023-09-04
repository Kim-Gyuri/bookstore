package springstudy.bookstore.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import springstudy.bookstore.domain.dto.file.CreateFileResponse;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class FileService {

   @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }


    public CreateFileResponse storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }
        // 원래 파일명 추출
        String originalFilename = multipartFile.getOriginalFilename();

        // uuid 저장파일명 생성
        String savedFileName = createStoreFileName(originalFilename);

        // 파일을 불러올 때 사용할 파일 경로 (예: /file:/users/.../nameh8787bghh33.png)
        String savedFilePath = fileDir + savedFileName;

        CreateFileResponse fileInfo = new CreateFileResponse();
        fileInfo.updateItemImg(originalFilename,savedFileName,savedFilePath);

        // 실제로 로컬에 uuid 파일명으로 저장하기
        multipartFile.transferTo(new File(savedFilePath));
        log.info("fileInfo={}", fileInfo.getSavePath());
        return fileInfo;

    }


    // uuid 파일명 생성 메서드
    private String createStoreFileName(String originalFilename) {
        String fileExtension = getFileExtension(originalFilename);
        String uuid = UUID.randomUUID().toString();

        //uuid.확장자 로 만들기
        String savedFileName = uuid + "." + fileExtension;
        return savedFileName;
    }

    // 확장자 추출 메서드
    private String getFileExtension(String originalFilename) {
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        return extension;
    }

}
