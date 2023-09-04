package springstudy.bookstore.service;


//@Slf4j
//@RequiredArgsConstructor
//@Component
//@Service
public class S3FileService {

   /*
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



    * uuid 파일명 생성 메서드
    private String createStoreFileName(String dirName, String originalName) {
        return dirName + "/" + UUID.randomUUID() + originalName;
    }

     *
     * convert() 메소드에서 로컬 프로젝트에 사진 파일이 생성되지만,
     * removeNewFile()을 통해서 바로 지워준다.
     *
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
    */
}
