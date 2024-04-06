package zzangdol.moodoodleapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import zzangdol.config.JpaConfig;
import zzangdol.feign.config.FeignConfig;

@Import({JpaConfig.class, FeignConfig.class})
@SpringBootApplication
public class MoodoodleApiApplication {

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "application-domain,application-infrastructure");
        SpringApplication.run(MoodoodleApiApplication.class, args);
    }

}
