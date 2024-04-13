package zzangdol.moodoodleapi.auth.business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import zzangdol.moodoodleapi.auth.implement.AuthService;
import zzangdol.moodoodleapi.auth.implement.EmailVerificationTokenService;
import zzangdol.moodoodleapi.auth.implement.VerificationCodeService;
import zzangdol.moodoodleapi.auth.presentation.dto.request.EmailVerificationRequest;
import zzangdol.moodoodleapi.auth.presentation.dto.request.SignUpRequest;
import zzangdol.ses.service.AwsSesService;

@Component
@RequiredArgsConstructor
public class AuthFacade {

    private final AuthService authService;
    private final AwsSesService awsSesService;
    private final VerificationCodeService verificationCodeService;
    private final EmailVerificationTokenService emailVerificationTokenService;

    public boolean sendVerificationEmail(String email) {
        String verificationCode = verificationCodeService.generateAndSaveCode(email);
        return awsSesService.sendVerificationEmail(email, verificationCode);
    }

    public String verifyEmail(EmailVerificationRequest request) {
        verificationCodeService.verifyCode(request.getEmail(), request.getCode());
        return emailVerificationTokenService.generateAndSaveCode(request.getEmail());
    }

    public Long signUp(String emailVerificationToken, SignUpRequest request) {
        emailVerificationTokenService.verifyToken(request.getEmail(), emailVerificationToken);
        return authService.signUp(request).getId();
    }

}
