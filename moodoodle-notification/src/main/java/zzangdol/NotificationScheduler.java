package zzangdol;

import java.time.LocalTime;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import zzangdol.notification.domain.FcmToken;
import zzangdol.user.dao.UserRepository;
import zzangdol.user.domain.User;

@RequiredArgsConstructor
@Component
public class NotificationScheduler {

    private final FCMService fcmService;
    private final UserRepository userRepository;

    private static final List<String> TITLES = List.of(
            "무두들: 오늘의 일기 시간이에요!",
            "무두들: 일기 쓸 시간입니다",
            "무두들: 오늘 하루 어땠나요?",
            "무두들: 일기 작성 시간!",
            "무두들: 오늘의 생각을 기록해보세요",
            "무두들: 하루를 마무리하며 일기를 써보세요",
            "무두들: 당신의 하루를 기록하세요",
            "무두들: 일기 쓰는 시간을 잊지 마세요",
            "무두들: 오늘의 일기를 써보세요",
            "무두들: 잠시 시간을 내어 일기를 써보세요"
    );

    private static final List<String> BODIES = List.of(
            "무두들에서 오늘 하루 있었던 일들을 일기에 적어보세요.",
            "무두들에 오늘의 감정과 경험을 남겨보세요.",
            "무두들에서 일기를 쓰며 하루를 정리해보세요.",
            "무두들에 오늘 하루를 돌아보며 일기를 작성해보세요.",
            "무두들에서 일기를 통해 하루를 마무리해보세요.",
            "무두들에 오늘 느낀 감정과 생각을 기록해보세요.",
            "무두들에서 일기를 쓰며 하루를 정리해보는 건 어떨까요?",
            "무두들에 오늘의 소중한 순간들을 남겨보세요.",
            "무두들에서 하루를 마무리하며 일기를 써보세요.",
            "무두들에 오늘 하루를 기록하며 내일을 준비해보세요."
    );

    @Scheduled(cron = "0,30 * * * * ?") // 30분 단위로 실행
    public void scheduleDailyNotification() {
        try {
            LocalTime now = LocalTime.now();
            int currentHour = now.getHour();
            int currentMinute = now.getMinute();

            List<User> users = userRepository.findUsersWithNotificationTime(currentHour, currentMinute);

            for (User user : users) {
                Random random = new Random();
                String title = TITLES.get(random.nextInt(TITLES.size()));
                String body = BODIES.get(random.nextInt(BODIES.size()));
                List<FcmToken> tokens = user.getFcmTokens();
                for (FcmToken token : tokens) {
                    fcmService.sendNotification(token.getToken(), title, body);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
