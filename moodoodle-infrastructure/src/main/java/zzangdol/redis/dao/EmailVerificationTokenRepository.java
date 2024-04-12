package zzangdol.redis.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import zzangdol.redis.domain.EmailVerificationToken;

@Repository
public interface EmailVerificationTokenRepository extends CrudRepository<EmailVerificationToken, String> {
}
