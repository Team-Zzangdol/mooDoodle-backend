package zzangdol.user.business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import zzangdol.user.presentation.dto.response.UserInfoResponse;
import zzangdol.user.domain.User;

@RequiredArgsConstructor
@Component
public class UserFacade {

    public UserInfoResponse getUserInfo(User user) {
        return UserMapper.toUserInfoResponse(user);
    }

}
