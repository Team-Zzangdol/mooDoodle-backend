package zzangdol.user.presentation.dto.request;

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

    private String nickname;
    private LocalTime notificationTime;

}
