package zzangdol.user.business;

import java.time.format.DateTimeFormatter;
import zzangdol.user.presentation.dto.response.UserInfoResponse;
import zzangdol.user.domain.User;

public class UserMapper {

    public static UserInfoResponse toUserInfoResponse(User user) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return UserInfoResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .notificationTime(user.getNotificationTime().format(formatter))
                .authProvider(user.getAuthProvider())
                .build();
    }

}
