package zzangdol.moodoodleapi.jwt;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zzangdol.member.dao.MemberRepository;
import zzangdol.member.domain.Member;
import zzangdol.moodoodlecommon.exception.GeneralException;
import zzangdol.moodoodlecommon.exception.custom.MemberNotFoundException;
import zzangdol.moodoodlecommon.response.status.ErrorStatus;
import zzangdol.redis.dao.RefreshTokenRepository;
import zzangdol.redis.domain.RefreshToken;

@RequiredArgsConstructor
@Service
public class JwtService {

    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 604800L;

    public JwtResponse issueToken(Member member) {
        JwtResponse jwtResponse = jwtProvider.generateToken(member);
        saveRefreshToken(jwtResponse.getRefreshToken(), member.getId(), REFRESH_TOKEN_EXPIRE_TIME);
        return jwtResponse;
    }

    public JwtResponse reissueToken(String refreshToken) {
        validateRefreshToken(refreshToken);
        refreshTokenRepository.deleteById(refreshToken);

        Claims claims = jwtProvider.parseClaims(refreshToken);
        String id = claims.getSubject();
        Member member = memberRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new MemberNotFoundException());

        JwtResponse jwtResponse = jwtProvider.generateToken(member);
        saveRefreshToken(jwtResponse.getRefreshToken(), member.getId(), REFRESH_TOKEN_EXPIRE_TIME);
        return jwtResponse;
    }

    private void validateRefreshToken(String refreshToken) {
        if (!refreshTokenRepository.existsById(refreshToken)) {
            throw new GeneralException(ErrorStatus.REFRESH_TOKEN_NOT_FOUND);
        }
    }

    private void saveRefreshToken(String token, Long memberId, Long ttl) {
        RefreshToken refreshToken = RefreshToken.builder()
                .token(token)
                .memberId(memberId)
                .ttl(ttl)
                .build();
        refreshTokenRepository.save(refreshToken);
    }

}
