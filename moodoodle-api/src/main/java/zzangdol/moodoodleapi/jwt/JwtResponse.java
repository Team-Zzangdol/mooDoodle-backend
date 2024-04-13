package zzangdol.moodoodleapi.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class JwtResponse {

    private String grantType;
    private String accessToken;
    private String refreshToken;

}
