package zzangdol.user.implement;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zzangdol.diary.dao.DiaryRepository;
import zzangdol.notification.dao.FcmTokenRepository;
import zzangdol.notification.domain.FcmToken;
import zzangdol.report.dao.ReportRepository;
import zzangdol.scrap.dao.CategoryRepository;
import zzangdol.scrap.dao.ScrapRepository;
import zzangdol.user.dao.UserRepository;
import zzangdol.user.domain.User;
import zzangdol.user.presentation.dto.request.PushNotificationRequest;
import zzangdol.user.presentation.dto.request.UserInfoUpdateRequest;
import zzangdol.user.presentation.dto.request.UserNicknameUpdateRequest;
import zzangdol.user.presentation.dto.request.UserNotificationTimeUpdateRequest;

@RequiredArgsConstructor
@Transactional
@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final CategoryRepository categoryRepository;
    private final ScrapRepository scrapRepository;
    private final ReportRepository reportRepository;
    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;
    private final FcmTokenRepository fcmTokenRepository;

    @Override
    public User updateUserInfo(User user, UserInfoUpdateRequest request) {
        user.updateNickname(request.getNickname());
        user.updateNotificationTime(request.getNotificationTime());
        return user;
    }

    @Override
    public User updateUserNickname(User user, UserNicknameUpdateRequest request) {
        user.updateNickname(request.getNickname());
        return user;
    }

    @Override
    public User updateUserNotificationTime(User user, UserNotificationTimeUpdateRequest request) {
        user.updateNotificationTime(request.getNotificationTime());
        return user;
    }

    @Override
    public User handlePushNotifications(User user, PushNotificationRequest request) {
        user.updatePushNotificationsEnabled(request.getPushNotificationsEnabled());

        if (Boolean.TRUE.equals(request.getPushNotificationsEnabled())) {
            storeFcmToken(request.getFcmToken(), user);
        } else {
            abortFcmToken(request.getFcmToken(), user);
        }

        return userRepository.save(user);
    }

    public void storeFcmToken(String token, User user) {
        Optional<FcmToken> optionalFcmToken = fcmTokenRepository.findByUserAndToken(user, token);

        optionalFcmToken.ifPresentOrElse(
                fcmToken -> {},
                () -> fcmTokenRepository.save(FcmToken.builder()
                        .token(token)
                        .user(user)
                        .build())
        );
    }

    public void abortFcmToken(String token, User user) {
        Optional<FcmToken> optionalFcmToken = fcmTokenRepository.findByUserAndToken(user, token);

        optionalFcmToken.ifPresentOrElse(
                fcmTokenRepository::delete,
                () -> {}
        );
    }

    @Override
    public void withDrawUser(User user) {
        categoryRepository.deleteByUser(user);
        scrapRepository.deleteByUser(user);
        reportRepository.deleteByUser(user);
        diaryRepository.deleteByUser(user);
        userRepository.delete(user);
    }

}
