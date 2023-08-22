package springstudy.bookstore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import springstudy.bookstore.domain.dto.FileInfoDto;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3FileService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

   // private final AmazonS3 amazonS3;

    public FileInfoDto storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
           return null;
        }

        // 원래 파일명 추출
        String originalFilename = multipartFile.getOriginalFilename();

        // uuid 저장파일명 생성
        String savedFileName = createStoreFileName(originalFilename);

        // 파일을 불러올 때 사용할 파일 경로 (예: /file:/users/.../nameh8787bghh33.png)
        String savedFilePath = bucket + savedFileName;

        FileInfoDto fileInfo = new FileInfoDto();
        fileInfo.updateItemImg(originalFilename,savedFileName,savedFilePath);

        // 실제로 로컬에 uuid 파일명으로 저장하기
        multipartFile.transferTo(new File(savedFilePath));
        log.info("fileInfo={}", fileInfo.getSavePath());
        return fileInfo;

    }

    // uuid 파일명 생성 메서드
    private String createStoreFileName(String originalFilename) {
        return UUID.randomUUID().toString().concat(getFileExtension(originalFilename));
    }

    // 확장자 추출 메서드
    private String getFileExtension(String originalFilename) {
        try {
            return originalFilename.substring(originalFilename.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + originalFilename + ") 입니다.");
        }
    }


}
