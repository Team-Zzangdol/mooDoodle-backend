package zzangdol.moodoodleapi.auth.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(example = "teamzzangdol@gmail.com")
    private String email;

    @Schema(example = "password1234")
    private String password;

    @Schema(example = "짱돌")
    private String nickname;

    @Schema(example = "21:00")
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
