package springstudy.bookstore.config;

//@Configuration
public class S3Config {
  /*
  //  @Value("${cloud.aws.region.static}")
    private String region;
  //  @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

  //  @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;
    //@Bean
    public AmazonS3Client amazonS3Client() {

        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        return (AmazonS3Client) AmazonS3ClientBuilder
                .standard()
               .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
    }

   */

}
