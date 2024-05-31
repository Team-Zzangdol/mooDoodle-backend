package zzangdol.auth.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zzangdol.user.domain.AuthProvider;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SocialLoginRequest {

    @Schema(description = "인증 제공자", example = "KAKAO")
    private AuthProvider authProvider;

    @Schema(description = "인증 코드", example = "authorization_code_from_provider")
    private String authorizationCode;

}