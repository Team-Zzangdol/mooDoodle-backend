package zzangdol.moodoodleapi.auth.presentation.dto.request;

import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zzangdol.member.domain.Member;
import zzangdol.member.domain.Role;
import zzangdol.member.domain.AuthProvider;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    private String email;
    private String password;
    private String nickname;
    private LocalTime notificationTime;

    public Member toEntity(AuthProvider authProvider, Role role, String encodedPassword) {
        return Member.builder()
                .email(email)
                .password(encodedPassword)
                .nickname(nickname)
                .authProvider(authProvider)
                .role(role)
                .notificationTime(notificationTime)
                .build();
    }

}
