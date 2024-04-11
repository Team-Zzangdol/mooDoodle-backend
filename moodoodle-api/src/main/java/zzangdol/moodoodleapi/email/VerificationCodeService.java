package zzangdol.moodoodleapi.email;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zzangdol.redis.dao.VerificationCodeRepository;
import zzangdol.redis.domain.VerificationCode;

@RequiredArgsConstructor
@Service
public class VerificationCodeService {

    private final VerificationCodeRepository verificationCodeRepository;

    public void saveVerificationCode(String id, String code, Long ttl) {
        VerificationCode verificationCode = VerificationCode.builder()
                .id(id)
                .code(code)
                .ttl(ttl)
                .build();
        verificationCodeRepository.save(verificationCode);
    }

    public String getVerificationCode(String id) {
        VerificationCode verificationCode = verificationCodeRepository.findById(id).orElse(null);
        if (verificationCode != null) {
            return verificationCode.getCode();
        }
        return null;
    }

}
