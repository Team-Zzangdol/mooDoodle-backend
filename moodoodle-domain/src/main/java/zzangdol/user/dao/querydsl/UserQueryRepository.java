package zzangdol.user.dao.querydsl;

import java.util.List;
import zzangdol.user.domain.User;

public interface UserQueryRepository {

    List<User> findUsersWithNotificationTime(int hour, int minute);

}