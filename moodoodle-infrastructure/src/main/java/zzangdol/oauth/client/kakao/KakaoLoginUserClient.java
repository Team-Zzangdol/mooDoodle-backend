package zzangdol.oauth.client.kakao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import zzangdol.oauth.dto.KakaoUserInfoResponse;

@Slf4j
@Component
public class KakaoLoginUserClient {

    private final WebClient webClient;

    private static final String USER_INFO_URI = "https://kapi.kakao.com/v2/user/me";

    public KakaoLoginUserClient() {
        this.webClient = WebClient.builder()
                .baseUrl(USER_INFO_URI)
                .build();
    }

    public KakaoUserInfoResponse getUserInfo(final String token) {
        return webClient.get()
                .uri("")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(KakaoUserInfoResponse.class)
                .block();
    }

}
