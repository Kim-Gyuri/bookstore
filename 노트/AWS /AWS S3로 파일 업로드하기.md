# 📌 AWS S3로 파일 업로드하기
프로젝트 구현 기능 중 판매할 상품 등록할 때 이미지 파일을 업로드 해야 하는데, <br> SpringBoot & AWS S3 연동하여 파일을 업로드하고 URL을 호출하여 이미지를 불러오도록 해보자. <br>

목차는 다음과 같다. <br>
+ AWS S3 버킷 생성 및 IAM 설정
  + AWS S3 관련 설정
  + 로컬 테스트
  + 배포 환경에서 사용하기
+ AmazonS3Config 설정 클래스
+ s3 파일 업로드 코드구현
+ s3 사용 관련 서비스 코드수정
+ s3 사용 관련 컨트롤러 코드수정
+ s3 사용 관련 뷰 코드수정
+ 참고자료
## AWS S3 버킷 생성 및 IAM 설정
S3를 사용하기 위해서는 IAM을 통하여 Key값을 발급 받아야한다.
+ AWS S3 Bucket 생성
+ IAM 사용자 권한 추가 <br> S3에 접근하기 위해서는 IAM 사용자에게 S3 접근 권한을 주고, 그 사용자의 액세스키, 비밀 엑세스 키를 사용해야 한다.
+ application/properties 에 s3 정보와 IAM 사용자 정보를 등록한다. 

> `주의사항` git에는 엑세스 키를 올리지 않게 (해킹으로 인한 과금) 조심해야 한다.

### 로컬 테스트
로컬에서 테스트하기 위해서 AWS S3에 필요한 정보를 application.properties에 추가한다. <br>
```
#s3
cloud.aws.s3.bucket= s3 버킷명
cloud.aws.credentials.access-key=  IAM 액세스 키
cloud.aws.credentials.secret-key= IAM 비밀 액세스 키
cloud.aws.region.static= ap-northeast-2
cloud.aws.stack.auto=false
```
### 배포 환경에서 사용하기
로컬이 아닌 배포 서버인 EC2에서 테스트해보기. <br>
AWS S3의 accessKey, secretKey는 Git의 관리 대상이 아니므로, 배포 항목에 포함되지 않는다. <br>
다음과 같이 설정을 해야 한다. <br>
+ EC2에 S3 Role을 추가한다.
+  AWS S3에 필요한 정보를 application.properties에 추가하여 깃허브에 업로드한다.
```
# s3
cloud.aws.s3.bucket= elasticbeanstalk-ap-northeast-2-574628083448
cloud.aws.credentials.instanceProfile=true
cloud.aws.region.static= ap-northeast-2
cloud.aws.stack.auto=false
```  

> `cloud.aws.stack.auto= false` <br>
> EC2에서 Spring Cloud 프로젝트를 실행시키면 기본으로 CloudFormation 구성을 시작한다. <br>
> 설정한 CloudFormation이 없으면 프로젝트 시작이 안되니, 해당 내용을 사용하지 않도록 false를 등록한다.

> `cloud.aws.credentials.instanceProfile= true` <br>
> AWS의 instanceProfile를 사용하도록 설정한다. (= "AWS Key들을 사용한다") <br>

## AmazonS3Config 설정 클래스를 만든다.
application.properties 파일에 작성한 값들을 읽어와서 AmazonS3Client 객체를 만들어 bean으로 주입해준 것이다. <br>
Amazon S3로 접근하기 위해 필요한 AmazonS3Client 클래스를 사용하기 위해 AmazonS3ClientBuilder 클래스로 객체를 생성한다. <br>
![s3Config - ](https://github.com/Kim-Gyuri/bookstore/assets/57389368/378e9512-94b3-45ec-bdae-0bcefc75c1c0)

## S3Uploader 작성하기
스프링부트에서 이미지 업로드 기능을 구현하기 위해, s3로 파일 업로드하는 코드를 구현해야 한다. <br> 코드의 순서는 다음과 같다. <br>
+ S3에 업로드하기위해선 MultipartFile을 File객체로 변환후 현재 프로젝트 경로에 업로드 한다.
+ 파일명을 UUID로 변환하여 S3에 업로드후 <br> FileInfoDTO(올리려는 파일이름, s3에 저장될 파일이름, s3 업로드된 파일경로 url)를 반환한다. 
+ 현재 프로젝트 경로에 생성되었던 파일을 제거한다.

> `FileInfoDTO를 반환`한 이유 <br>
> 상품 Item 엔티티에 필요한 Image 정보를 DTO로 만들어 사용하기 위해 FileInfoDTO로 반환하도록 작성하였다.

`S3Uploader 코드` <br>
```java
package springstudy.bookstore.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import springstudy.bookstore.domain.dto.FileInfoDto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
@Service
public class S3FileService {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    // MultipartFile을 전달받아 File로 전환한 후 S3에 업로드
    public FileInfoDto upload(MultipartFile multipartFile, String dirName) throws IOException {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));

        return upload(multipartFile, dirName, uploadFile);
    }

    private FileInfoDto upload(MultipartFile multipartFile, String dirName, File uploadFile) {
        FileInfoDto fileInfoDto = new FileInfoDto();

        String originFileName = multipartFile.getOriginalFilename(); // 올리려는 파일이름
        String fileName = createStoreFileName(dirName, multipartFile.getOriginalFilename()); // s3에 저장될 파일 이름
        String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드

        log.info("upload -> uploadFile{}", uploadFile); //로그로 확인해봄
        log.info("upload -> dirName{}", dirName);
        log.info("upload -> originName{}", originFileName);

        removeNewFile(uploadFile);

        fileInfoDto.updateItemImg(originFileName, fileName, uploadImageUrl);
        return fileInfoDto;
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(
                new PutObjectRequest(bucket, fileName, uploadFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead)	// PublicRead 권한으로 업로드 됨
        );
        log.info("getFullPath={}", amazonS3Client.getUrl(bucket, fileName).toString());
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }


    // uuid 파일명 생성 메서드
    private String createStoreFileName(String dirName, String originalName) {
        return dirName + "/" + UUID.randomUUID() + originalName;
    }

    /*
     * convert() 메소드에서 로컬 프로젝트에 사진 파일이 생성되지만,
     * removeNewFile()을 통해서 바로 지워준다.
     */
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
```

> `겪었던 오류` <br>
>로컬에서는 원본 이미지 파일을 getOriginalFilename()으로 파일 이름을 가져와서 File 객체를 만들 수 있었지만, <br>
>EC2 서버에 배포한 곳에서는 getOriginalFilename() 했을 때 "MultipartFile -> File 변환오류"가 터졌다. <br>
>그래서 "getOriginalFilename() -> UUID.randomUUID()"로 파일이름을 생성하게 했다.


## ItemImageService에 코드 변경
s3Uploader.upload에서 매개변수로 s3 Bucket 파일이름(내가 생성한 파일 "test"로)을 지정한다. <br>
s3Uploader에서 반환받은 DTO로 상품 이미지 엔티티를 저장한다. <br>
```java
    public Long saveItemImg_s3(ItemInfoDto itemInfo, MultipartFile multipartFile) throws IOException {
        FileInfoDto fileInfo = s3FileService.upload(multipartFile, "test");

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
```

## Controller 작성하기
상품 등록 서비스 로직을, 방금 만든 itemService.saveItem_s3() 호출하도록 바꾼다. <br>
```java
    @PostMapping("/item/new")
    public String itemNew_s3(@Login User loginUser, @Validated @ModelAttribute ItemFormDto itemFormDto, BindingResult bindingResult,
                          Model model, @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList, RedirectAttributes redirectAttributes) throws IOException {

        if (loginUser == null) {
            return "login/loginForm";
        }

        log.info("post-> loginUser info{}", loginUser.toString());
        if (itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null) {
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "item/addItemForm";
        }

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "item/addItemForm";
        }

        // 성공로직
        Long id = itemService.saveItem_s3(loginUser, itemFormDto, itemImgFileList);
        log.info("itemInfo={}", itemService.findById(id).toString());
        redirectAttributes.addAttribute("itemId", id);
        return "redirect:/bookstore/item/{itemId}";
    }
```

## 뷰 파일 작성
`<img> 태그`로 이미지를 조회하도록 작성하였다. <br> 매개변수로 파일의 url 경로(filename)를 받아, UrlResource로 이미지 파일을 읽어서 @ResponseBody로 이미지 바이너리를 반환한다. <br>
```java
    @ResponseBody
    @GetMapping("{fileId}")
    public Resource download(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource(filename);
    }
```

등록한 상품 이미지는 <img> 태그를 사용하여 출력한다.
```html
<div th:each="itemImg : ${item.itemImgDtoList}">
             <img class="card-img-fake2" th:if="${not #strings.isEmpty(itemImg.savePath)}"
                        th:src="|${itemImg.savePath}|" /> 
</div>
```


## 참고자료
+ [S3 버킷 프리티어 생성 - 블로그 글](https://dev-elena-k.tistory.com/15)
+ [S3 버킷에 업로드 하기](https://dev-elena-k.tistory.com/16)
+ [[SpringBoot] AWS S3로 이미지 업로드하기 - 블로그 글](https://velog.io/@chaeri93/SpringBoot-AWS-S3%EB%A1%9C-%EC%9D%B4%EB%AF%B8%EC%A7%80-%EC%97%85%EB%A1%9C%EB%93%9C%ED%95%98%EA%B8%B0)
+ [S3_연동하기 - github 글](https://github.com/jojoldu/blog-code/blob/master/springboot-aws-tui/S3_%EC%97%B0%EB%8F%99%ED%95%98%EA%B8%B0.md)
