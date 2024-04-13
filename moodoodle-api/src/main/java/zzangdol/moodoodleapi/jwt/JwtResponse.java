package zzangdol.moodoodleapi.jwt;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class JwtResponse {

    @Schema(example = "Bearer")
    private String grantType;

    @Schema(example = "eyJhbGciOiJIUzI1NiJ9.eyJzdW...")
    private String accessToken;

    @Schema(example = "eyJhbGciOiJIUzI1NiJ9.eyJzdW...")
    private String refreshToken;

}
