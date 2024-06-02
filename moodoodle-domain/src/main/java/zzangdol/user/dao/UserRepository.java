package zzangdol.user.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zzangdol.user.dao.querydsl.UserQueryRepository;
import zzangdol.user.domain.AuthProvider;
import zzangdol.user.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserQueryRepository {

    Optional<User> findByAuthProviderAndEmail(AuthProvider authProvider, String email);

}
