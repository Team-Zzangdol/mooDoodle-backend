package zzangdol.auth.implement;

import java.security.SecureRandom;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zzangdol.auth.presentation.dto.response.EmailVerificationTokenResponse;
import zzangdol.moodoodlecommon.exception.custom.InvalidTokenException;
import zzangdol.redis.dao.EmailVerificationTokenRepository;
import zzangdol.redis.domain.EmailVerificationToken;

@RequiredArgsConstructor
@Service
public class EmailVerificationTokenService {

    private final EmailVerificationTokenRepository emailVerificationTokenRepository;

    public EmailVerificationTokenResponse generateAndSaveCode(String id) {
        String token = generateSecureToken();
        saveEmailVerificationToken(id, token, 86400L);
        return EmailVerificationTokenResponse.builder()
                .emailVerificationToken(token)
                .build();
    }

    private String generateSecureToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[24]; // 192 bits
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    public void saveEmailVerificationToken(String id, String token, Long ttl) {
        EmailVerificationToken emailVerificationToken = EmailVerificationToken.builder()
                .id(id)
                .token(token)
                .ttl(ttl)
                .build();
        emailVerificationTokenRepository.save(emailVerificationToken);
    }

    public boolean verifyToken(String id, String token) {
        EmailVerificationToken storedToken = emailVerificationTokenRepository.findById(id).orElse(null);
        if (storedToken == null || !storedToken.getToken().equals(token)) {
            throw InvalidTokenException.EXCEPTION;
        }
        deleteEmailVerificationToken(id);
        return true;
    }

    private void deleteEmailVerificationToken(String id) {
        emailVerificationTokenRepository.deleteById(id);
    }

}
