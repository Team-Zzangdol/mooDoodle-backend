package zzangdol.user.implement;

import zzangdol.user.domain.User;

public interface UserQueryService {

    User findById(Long id);

}