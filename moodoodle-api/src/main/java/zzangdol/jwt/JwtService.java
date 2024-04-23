package zzangdol.jwt;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zzangdol.exception.GeneralException;
import zzangdol.exception.custom.UserNotFoundException;
import zzangdol.redis.dao.RefreshTokenRepository;
import zzangdol.redis.domain.RefreshToken;
import zzangdol.response.status.ErrorStatus;
import zzangdol.user.dao.UserRepository;
import zzangdol.user.domain.User;

@RequiredArgsConstructor
@Service
public class JwtService {

    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 604800L;

    public JwtResponse issueToken(User user) {
        JwtResponse jwtResponse = jwtProvider.generateToken(user);
        saveRefreshToken(jwtResponse.getRefreshToken(), user.getId(), REFRESH_TOKEN_EXPIRE_TIME);
        return jwtResponse;
    }

    public JwtResponse reissueToken(String refreshToken) {
        validateRefreshToken(refreshToken);
        refreshTokenRepository.deleteById(refreshToken);

        Claims claims = jwtProvider.parseClaims(refreshToken);
        String id = claims.getSubject();
        User user = userRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        JwtResponse jwtResponse = jwtProvider.generateToken(user);
        saveRefreshToken(jwtResponse.getRefreshToken(), user.getId(), REFRESH_TOKEN_EXPIRE_TIME);
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
