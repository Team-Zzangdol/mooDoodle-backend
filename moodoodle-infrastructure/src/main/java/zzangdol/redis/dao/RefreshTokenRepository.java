package zzangdol.redis.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import zzangdol.redis.domain.RefreshToken;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
