package zzangdol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MoodoodleApiApplication {

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "application-api, application-domain, application-infrastructure");
        SpringApplication.run(MoodoodleApiApplication.class, args);
    }

}
