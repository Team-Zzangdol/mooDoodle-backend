package zzangdol.user.implement;

import zzangdol.user.domain.User;
import zzangdol.user.presentation.dto.request.PushNotificationRequest;
import zzangdol.user.presentation.dto.request.UserInfoUpdateRequest;
import zzangdol.user.presentation.dto.request.UserNicknameUpdateRequest;
import zzangdol.user.presentation.dto.request.UserNotificationTimeUpdateRequest;

public interface UserCommandService {

    User updateUserInfo(User user, UserInfoUpdateRequest request);

    User updateUserNickname(User user, UserNicknameUpdateRequest request);

    User updateUserNotificationTime(User user, UserNotificationTimeUpdateRequest request);

    User handlePushNotifications(User user, PushNotificationRequest request);

    void withDrawUser(User user);

}
