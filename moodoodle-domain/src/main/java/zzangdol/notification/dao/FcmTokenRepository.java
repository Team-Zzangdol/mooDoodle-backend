package zzangdol.notification.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import zzangdol.notification.domain.FcmToken;
import zzangdol.user.domain.User;

public interface FcmTokenRepository extends JpaRepository<FcmToken, Long> {

    Optional<FcmToken> findByUserAndToken(User user, String token);

}
