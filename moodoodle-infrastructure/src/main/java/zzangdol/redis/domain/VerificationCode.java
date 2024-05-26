package zzangdol.redis.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@NoArgsConstructor
@RedisHash(value = "verificationCode")
public class VerificationCode {

    @Id
    private String id;

    @Indexed
    private String code;

    @TimeToLive
    private Long ttl;

    @Builder
    public VerificationCode(String id, String code, Long ttl) {
        this.id = id;
        this.code = code;
        this.ttl = ttl;
    }

}
