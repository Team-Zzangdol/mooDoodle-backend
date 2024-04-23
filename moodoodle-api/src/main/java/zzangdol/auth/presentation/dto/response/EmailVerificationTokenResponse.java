package zzangdol.auth.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmailVerificationTokenResponse {

    @Schema(example = "H1i9sNC2DhTcehPkKklwgYrQ5h_b1KwC")
    private String emailVerificationToken;

}