package zzangdol.user.presentation.dto.request;

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
public class UserInfoUpdateRequest {

    @Schema(example = "짱짱돌")
    private String nickname;

    @Schema(example = "20:00")
    private LocalTime notificationTime;

}
