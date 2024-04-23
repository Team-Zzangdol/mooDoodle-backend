package zzangdol.user.implement;

import zzangdol.user.domain.User;
import zzangdol.user.presentation.dto.request.UserInfoUpdateRequest;

public interface UserCommandService {

    User updateUserInfo(User user, UserInfoUpdateRequest request);

}
