package zzangdol.user.implement;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zzangdol.user.domain.User;
import zzangdol.user.presentation.dto.request.UserInfoUpdateRequest;

@Transactional
@Service
public class UserCommandServiceImpl implements UserCommandService {

    @Override
    public User updateUserInfo(User user, UserInfoUpdateRequest request) {
        user.updateNickname(request.getNickname());
        user.updateNotificationTime(request.getNotificationTime());
        return user;
    }

}
