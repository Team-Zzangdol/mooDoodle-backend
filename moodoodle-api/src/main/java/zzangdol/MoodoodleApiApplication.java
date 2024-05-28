package zzangdol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MoodoodleApiApplication {

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "application-api, application-domain, application-infrastructure");
        SpringApplication.run(MoodoodleApiApplication.class, args);
    }

}
