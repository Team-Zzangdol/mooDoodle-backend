package zzangdol.user.implement;

import zzangdol.user.domain.User;
import zzangdol.user.presentation.dto.request.PushNotificationRequest;
import zzangdol.user.presentation.dto.request.UserInfoUpdateRequest;

public interface UserCommandService {

    User updateUserInfo(User user, UserInfoUpdateRequest request);

    User handlePushNotifications(User user, PushNotificationRequest request);

    void withDrawUser(User user);

}
