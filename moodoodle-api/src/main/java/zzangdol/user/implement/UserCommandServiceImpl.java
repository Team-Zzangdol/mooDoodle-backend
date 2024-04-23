package zzangdol.user.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zzangdol.diary.dao.DiaryRepository;
import zzangdol.report.dao.ReportRepository;
import zzangdol.scrap.dao.ScrapRepository;
import zzangdol.user.dao.UserRepository;
import zzangdol.user.domain.User;
import zzangdol.user.presentation.dto.request.UserInfoUpdateRequest;

@RequiredArgsConstructor
@Transactional
@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final ScrapRepository scrapRepository;
    private final ReportRepository reportRepository;
    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;

    @Override
    public User updateUserInfo(User user, UserInfoUpdateRequest request) {
        user.updateNickname(request.getNickname());
        user.updateNotificationTime(request.getNotificationTime());
        return user;
    }

    @Override
    public void withDrawUser(User user) {
        scrapRepository.deleteByUser(user);
        reportRepository.deleteByUser(user);
        diaryRepository.deleteByUser(user);
        userRepository.delete(user);
    }

}
