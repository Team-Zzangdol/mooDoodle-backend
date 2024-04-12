package zzangdol.moodoodleapi.member.business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import zzangdol.moodoodleapi.member.implement.VerificationCodeService;
import zzangdol.moodoodleapi.member.presentation.dto.EmailVerificationRequest;
import zzangdol.ses.service.AwsSesService;

@Component
@RequiredArgsConstructor
public class MemberFacade {

    private final VerificationCodeService verificationCodeService;
    private final AwsSesService awsSesService;

    public boolean sendVerificationEmail(String email) {
        String verificationCode = verificationCodeService.generateAndSaveCode(email);
        return awsSesService.sendVerificationEmail(email, verificationCode);
    }

    public boolean verifyEmail(EmailVerificationRequest request) {
        return verificationCodeService.verifyCode(request.getEmail(), request.getCode());
    }
}
