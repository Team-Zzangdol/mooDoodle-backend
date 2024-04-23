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
public class SignInRequest {

    @Schema(example = "teamzzangdol@gmail.com")
    private String email;

    @Schema(example = "password1234")
    private String password;

}
