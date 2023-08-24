package springstudy.bookstore.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import springstudy.bookstore.domain.dto.FileInfoDto;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3FileService {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String regionStatic;
    private AmazonS3 amazonS3;

    @PostConstruct
    private void setS3Client() {
       // AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        amazonS3 = AmazonS3ClientBuilder.standard()
                .withRegion(regionStatic)
         //       .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }
    public String getFullPath(String filename) {
        return amazonS3.getUrl(bucket, filename).toString();
    }

    public FileInfoDto upload(MultipartFile multipartFile, String dirName) throws IOException {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));

        return upload(uploadFile, dirName, multipartFile.getOriginalFilename());
    }


    private FileInfoDto upload(File uploadFile, String dirName, String originalName) {
        FileInfoDto fileInfo = new FileInfoDto();

        String fileName = dirName + "/" + UUID.randomUUID() + originalName; // S3에 저장된 파일 이름
        String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드

        fileInfo.updateItemImg(originalName,fileName,uploadImageUrl);
        log.info("img s3 url ={}", uploadImageUrl);

        removeNewFile(uploadFile);
        return fileInfo;
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(
                new PutObjectRequest(bucket, fileName, uploadFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead)	// PublicRead 권한으로 업로드 됨
        );
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }


    private void removeNewFile(File targetFile) {
        if(targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        }else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }
    private Optional<File> convert(MultipartFile file) throws  IOException {
        File convertFile = new File(System.getProperty("user.dir") + "/" + UUID.randomUUID());
        if(convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

}
