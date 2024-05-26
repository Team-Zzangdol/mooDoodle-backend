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
@RedisHash(value = "refreshToken")
public class RefreshToken {

    @Id
    private String token;

    @Indexed
    private Long memberId;

    @TimeToLive
    private Long ttl;

    @Builder
    public RefreshToken(String token, Long memberId, Long ttl) {
        this.token = token;
        this.memberId = memberId;
        this.ttl = ttl;
    }

}
