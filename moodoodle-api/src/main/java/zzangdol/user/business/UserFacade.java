package zzangdol.user.business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import zzangdol.user.implement.UserCommandService;
import zzangdol.user.presentation.dto.request.UserInfoUpdateRequest;
import zzangdol.user.presentation.dto.response.UserInfoResponse;
import zzangdol.user.domain.User;

@RequiredArgsConstructor
@Component
public class UserFacade {

    private final UserCommandService userCommandService;

    public UserInfoResponse getUserInfo(User user) {
        return UserMapper.toUserInfoResponse(user);
    }

    public UserInfoResponse updateUserInfo(User user, UserInfoUpdateRequest request) {
        return UserMapper.toUserInfoResponse(userCommandService.updateUserInfo(user, request));
    }

}
