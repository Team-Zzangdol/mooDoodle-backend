package zzangdol.moodoodleapi.auth.implement;

import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zzangdol.moodoodlecommon.exception.custom.VerificationCodeException;
import zzangdol.moodoodlecommon.response.status.ErrorStatus;
import zzangdol.redis.dao.VerificationCodeRepository;
import zzangdol.redis.domain.VerificationCode;

@RequiredArgsConstructor
@Service
public class VerificationCodeService {

    private final VerificationCodeRepository verificationCodeRepository;

    public String generateAndSaveCode(String id) {
        String code = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));
        saveVerificationCode(id, code, 300L);
        return code;
    }

    public void saveVerificationCode(String id, String code, Long ttl) {
        VerificationCode verificationCode = VerificationCode.builder()
                .id(id)
                .code(code)
                .ttl(ttl)
                .build();
        verificationCodeRepository.save(verificationCode);
    }

    public boolean verifyCode(String id, String code) {
        VerificationCode storedCode = verificationCodeRepository.findById(id).orElse(null);
        if (storedCode == null) {
            throw new VerificationCodeException(ErrorStatus.VERIFICATION_CODE_EXPIRED);
        }
        if (!storedCode.getCode().equals(code)) {
            throw new VerificationCodeException(ErrorStatus.INVALID_VERIFICATION_CODE);
        }
        deleteVerificationCode(id);
        return true;
    }

    private void deleteVerificationCode(String id) {
        verificationCodeRepository.deleteById(id);
    }

}
