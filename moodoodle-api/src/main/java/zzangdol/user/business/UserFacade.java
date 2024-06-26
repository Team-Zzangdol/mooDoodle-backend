package zzangdol.user.business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import zzangdol.user.implement.UserCommandService;
import zzangdol.user.presentation.dto.request.PushNotificationRequest;
import zzangdol.user.presentation.dto.request.UserInfoUpdateRequest;
import zzangdol.user.presentation.dto.request.UserNicknameUpdateRequest;
import zzangdol.user.presentation.dto.request.UserNotificationTimeUpdateRequest;
import zzangdol.user.presentation.dto.response.UserInfoResponse;
import zzangdol.user.domain.User;

@RequiredArgsConstructor
@Component
public class UserFacade {

    private final UserCommandService userCommandService;

    public UserInfoResponse getUserInfo(User user) {
        return UserMapper.toUserInfoResponse(user);
    }

    public UserInfoResponse updateUserInfo(User user, UserInfoUpdateRequest request) {
        return UserMapper.toUserInfoResponse(userCommandService.updateUserInfo(user, request));
    }

    public UserInfoResponse updateUserNickname(User user, UserNicknameUpdateRequest request) {
        return UserMapper.toUserInfoResponse(userCommandService.updateUserNickname(user, request));
    }

    public UserInfoResponse updateUserNotificationTime(User user, UserNotificationTimeUpdateRequest request) {
        return UserMapper.toUserInfoResponse(userCommandService.updateUserNotificationTime(user, request));
    }

    public boolean withDrawUser(User user) {
        userCommandService.withDrawUser(user);
        return true;
    }

    public void handlePushNotifications(User user, PushNotificationRequest request) {
        userCommandService.handlePushNotifications(user, request);
    }
}
