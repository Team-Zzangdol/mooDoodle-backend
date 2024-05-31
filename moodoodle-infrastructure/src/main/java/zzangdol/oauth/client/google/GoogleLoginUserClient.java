package zzangdol.oauth.client.google;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import zzangdol.oauth.dto.GoogleUserInfoResponse;

@Slf4j
@Component
public class GoogleLoginUserClient {

    private final WebClient webClient;

    private static final String USER_INFO_URI = "https://www.googleapis.com/oauth2/v3/userinfo";

    public GoogleLoginUserClient() {
        this.webClient = WebClient.builder()
                .baseUrl(USER_INFO_URI)
                .build();
    }

    public GoogleUserInfoResponse getUserInfo(final String token) {
        return webClient.get()
                .uri("")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(GoogleUserInfoResponse.class)
                .block();
    }

}
