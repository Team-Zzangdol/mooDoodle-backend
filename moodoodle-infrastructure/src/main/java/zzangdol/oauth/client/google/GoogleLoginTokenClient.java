package zzangdol.oauth.client.google;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import zzangdol.oauth.dto.GoogleTokenResponse;

@Slf4j
@Component
public class GoogleLoginTokenClient {

    private WebClient webClient;

    @Value("${google.token.uri}")
    private String TOKEN_URI;

    @Value("${google.redirect.uri}")
    private String REDIRECT_URI;

    @Value("${google.grant.type}")
    private String GRANT_TYPE;

    @Value("${google.client.id}")
    private String CLIENT_ID;

    @Value("${google.client.secret}")
    private String CLIENT_SECRET;

    @Value("${google.scope}")
    private String SCOPE;

    @Value("${google.response_type}")
    private String RESPONSE_TYPE;

    @PostConstruct
    private void init() {
        this.webClient = WebClient.builder()
                .baseUrl(TOKEN_URI)
                .build();
    }

    public GoogleTokenResponse getTokenInfo(final String code) {
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
                .bodyToMono(GoogleTokenResponse.class)
                .block();
    }

}
