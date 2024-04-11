package zzangdol.moodoodleapi.email;

import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Component;

@Component
public class VerificationCodeGenerator {

    public String generate() {
        int randomNum = ThreadLocalRandom.current().nextInt(100000, 1000000);
        return String.valueOf(randomNum);
    }

}
