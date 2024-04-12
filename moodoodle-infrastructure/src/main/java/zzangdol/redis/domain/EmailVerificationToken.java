package zzangdol.redis.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@RedisHash(value = "emailVerificationToken")
public class EmailVerificationToken {

    @Id
    private String id;

    @Indexed
    private String token;

    @TimeToLive
    private Long ttl;

    @Builder
    public EmailVerificationToken(String id, String token, Long ttl) {
        this.id = id;
        this.token = token;
        this.ttl = ttl;
    }

}
