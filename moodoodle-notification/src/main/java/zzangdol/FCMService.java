package zzangdol;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
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
        Message message = Message.builder()
                .putData("title", title)
                .putData("body", body)
                .setToken(token)
                .build();

        String response = FirebaseMessaging.getInstance().send(message);
        System.out.println("Successfully sent message: " + response);
    }

    public void sendNotificationToAllUsers(String title, String body) throws Exception {
        List<FcmToken> tokens = fcmTokenRepository.findAll();
        for (FcmToken token : tokens) {
            sendNotification(token.getToken(), title, body);
        }
    }

}