package zzangdol.ses.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;

@Configuration
public class AwsSesConfig {

    @Value("${cloud.aws.ses.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.ses.credentials.secret-key}")
    private String secretKey;

    @Bean
    public SesClient sesClient() {
        AwsBasicCredentials awsBasicCredentials =
                AwsBasicCredentials.create(this.accessKey, this.secretKey);
        return SesClient.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))
                .region(Region.AP_NORTHEAST_2)
                .build();
    }

}
