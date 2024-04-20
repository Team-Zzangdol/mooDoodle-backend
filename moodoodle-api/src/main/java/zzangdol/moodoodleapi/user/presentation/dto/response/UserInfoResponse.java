package zzangdol.moodoodleapi.user.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zzangdol.user.domain.AuthProvider;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {

    private Long id;
    private String email;
    private String nickname;
    private String notificationTime;
    private AuthProvider authProvider;

}
