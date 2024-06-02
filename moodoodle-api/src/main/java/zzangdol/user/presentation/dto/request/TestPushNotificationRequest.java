package zzangdol.user.presentation.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestPushNotificationRequest {

    private String fcmToken;
    private String title;
    private String body;

}
