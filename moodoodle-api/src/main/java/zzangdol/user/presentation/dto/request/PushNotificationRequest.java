package zzangdol.user.presentation.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PushNotificationRequest {

    private Boolean pushNotificationsEnabled;
    private String fcmToken;

}
