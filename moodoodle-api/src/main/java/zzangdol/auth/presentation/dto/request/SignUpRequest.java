package zzangdol.auth.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @Schema(example = "teamzzangdol@gmail.com")
    private String email;

    @Schema(example = "password1234")
    private String password;

    @Schema(example = "짱돌")
    private String nickname;

    @Schema(example = "21:00")
    private LocalTime notificationTime;

}
