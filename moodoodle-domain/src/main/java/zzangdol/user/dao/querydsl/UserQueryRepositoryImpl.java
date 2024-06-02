package zzangdol.user.dao.querydsl;

import static zzangdol.user.domain.QUser.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import zzangdol.user.domain.User;

@RequiredArgsConstructor
@Repository
public class UserQueryRepositoryImpl implements UserQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<User> findUsersWithNotificationTime(int hour, int minute) {
        return queryFactory.selectFrom(user)
                .where(user.notificationTime.hour().eq(hour)
                        .and(user.notificationTime.minute().eq(minute)))
                .fetch();
    }

}
