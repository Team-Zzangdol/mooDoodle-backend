package zzangdol.moodoodlecommon.helper;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@RequiredArgsConstructor
public class SpringEnvironmentHelper {

    private final Environment environment;

    private final String DEV = "dev";
    private final String PROD = "prod";

    private final List<String> DEV_AND_PROD = List.of("dev", "prod");

    public Boolean isDevProfile() {
        String[] activeProfiles = environment.getActiveProfiles();
        List<String> currentProfile = Arrays.stream(activeProfiles).toList();
        return currentProfile.contains(DEV);
    }

    public Boolean isProdProfile() {
        String[] activeProfiles = environment.getActiveProfiles();
        List<String> currentProfile = Arrays.stream(activeProfiles).toList();
        return currentProfile.contains(PROD);
    }

    public Boolean isDevAndProdProfile() {
        String[] activeProfiles = environment.getActiveProfiles();
        List<String> currentProfile = Arrays.stream(activeProfiles).toList();
        return CollectionUtils.containsAny(DEV_AND_PROD, currentProfile);
    }
}