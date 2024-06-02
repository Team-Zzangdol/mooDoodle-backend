package zzangdol;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import zzangdol.notification.dao.FcmTokenRepository;
import zzangdol.notification.domain.FcmToken;

@Slf4j
@RequiredArgsConstructor
@Service
public class FCMService {

    private final FcmTokenRepository fcmTokenRepository;

    public void sendNotification(String token, String title, String body) throws Exception {
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        Message message = Message.builder()
                .setNotification(notification)
                .setToken(token)
                .build();

        String response = FirebaseMessaging.getInstance().send(message);
        log.info("Successfully sent message: " + response);
    }

    public void sendNotificationToAllUsers(String title, String body) throws Exception {
        List<FcmToken> tokens = fcmTokenRepository.findAll();
        for (FcmToken token : tokens) {
            sendNotification(token.getToken(), title, body);
        }
    }

}