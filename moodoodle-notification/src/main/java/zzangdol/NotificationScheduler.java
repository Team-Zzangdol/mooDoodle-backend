package zzangdol;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class NotificationScheduler {

    private final FCMService fcmService;

    @Scheduled(cron = "0 0 21 * * ?")
    public void scheduleDailyNotification() {
        try {
            String title = "Daily Reminder";
            String body = "This is your daily reminder!";
            fcmService.sendNotificationToAllUsers(title, body);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}