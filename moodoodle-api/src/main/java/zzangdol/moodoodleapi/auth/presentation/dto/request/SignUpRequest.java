package zzangdol.moodoodleapi.auth.presentation.dto.request;

import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zzangdol.member.domain.Member;
import zzangdol.member.domain.Role;
import zzangdol.member.domain.SocialProvider;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    private String email;
    private String password;
    private String nickname;
    private LocalTime notificationTime;

    public Member toEntity(SocialProvider socialProvider, Role role) {
        return Member.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .socialProvider(socialProvider)
                .role(role)
                .notificationTime(notificationTime)
                .build();
    }

}
