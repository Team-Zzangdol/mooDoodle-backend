package zzangdol.redis.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import zzangdol.redis.domain.VerificationCode;

@Repository
public interface VerificationCodeRepository extends CrudRepository<VerificationCode, String> {
}
