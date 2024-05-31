package zzangdol.oauth.client;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import zzangdol.oauth.dto.KakaoTokenResponse;

@Slf4j
@Component
public class KakaoLoginTokenClient {

    private WebClient webClient;
    @Value("${kakao.token.uri}")
    private String TOKEN_URI;
    @Value("${kakao.redirect.uri}")
    private String REDIRECT_URI;
    @Value("${kakao.grant.type}")
    private String GRANT_TYPE;
    @Value("${kakao.client.id}")
    private String CLIENT_ID;

    @Value("${kakao.client.secret}")
    private String CLIENT_SECRET;

    @PostConstruct
    private void init() {
        this.webClient = WebClient.builder()
                .baseUrl(TOKEN_URI)
                .build();
    }

    public KakaoTokenResponse getTokenInfo(final String code) {
        log.info("Requesting token with code: {}", code);

        return webClient.post()
                .uri("")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("grant_type", GRANT_TYPE)
                        .with("client_id", CLIENT_ID)
                        .with("redirect_uri", REDIRECT_URI)
                        .with("code", code)
                        .with("client_secret", CLIENT_SECRET))
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), clientResponse -> clientResponse.bodyToMono(String.class)
                        .flatMap(response -> {
                            log.error("Error Response: {}", response);
                            return Mono.error(new RuntimeException("Error Response: " + response));
                        }))
                .bodyToMono(KakaoTokenResponse.class)
                .doOnNext(response -> log.info("Token Response: {}", response))
                .block();
    }

}
