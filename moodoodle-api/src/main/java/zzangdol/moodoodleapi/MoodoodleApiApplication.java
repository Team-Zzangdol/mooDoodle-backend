package zzangdol.moodoodleapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import zzangdol.config.DomainConfig;
import zzangdol.config.InfrastructureConfig;
import zzangdol.feign.config.FeignConfig;

@Import({DomainConfig.class, InfrastructureConfig.class, FeignConfig.class})
@SpringBootApplication
public class MoodoodleApiApplication {

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "application-api, application-domain, application-infrastructure");
        SpringApplication.run(MoodoodleApiApplication.class, args);
    }

}
