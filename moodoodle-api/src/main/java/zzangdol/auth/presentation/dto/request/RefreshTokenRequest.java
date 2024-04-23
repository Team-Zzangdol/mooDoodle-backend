package zzangdol.auth.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenRequest {

    @Schema(example = "eyJhbGciOiJIUzI1NiJ9.eyJzdW...")
    private String refreshToken;

}