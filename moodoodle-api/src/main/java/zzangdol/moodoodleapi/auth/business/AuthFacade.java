package zzangdol.moodoodleapi.auth.business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import zzangdol.moodoodleapi.auth.implement.AuthService;
import zzangdol.moodoodleapi.auth.implement.EmailVerificationTokenService;
import zzangdol.moodoodleapi.auth.implement.VerificationCodeService;
import zzangdol.moodoodleapi.auth.presentation.dto.request.EmailVerificationRequest;
import zzangdol.moodoodleapi.auth.presentation.dto.request.SignInRequest;
import zzangdol.moodoodleapi.auth.presentation.dto.request.SignUpRequest;
import zzangdol.moodoodleapi.auth.presentation.dto.response.EmailVerificationTokenResponse;
import zzangdol.moodoodleapi.jwt.JwtResponse;
import zzangdol.moodoodleapi.jwt.JwtService;
import zzangdol.moodoodlecommon.exception.custom.UserCredentialsException;
import zzangdol.moodoodlecommon.response.status.ErrorStatus;
import zzangdol.ses.service.AwsSesService;
import zzangdol.user.domain.User;

@RequiredArgsConstructor
@Component
public class AuthFacade {

    private final AuthService authService;
    private final JwtService jwtService;
    private final AwsSesService awsSesService;
    private final VerificationCodeService verificationCodeService;
    private final EmailVerificationTokenService emailVerificationTokenService;

    public boolean sendVerificationEmail(String email) {
        if (!authService.isEmailAvailable(email)) {
            throw new UserCredentialsException(ErrorStatus.EMAIL_ALREADY_EXISTS);
        }
        String verificationCode = verificationCodeService.generateAndSaveCode(email);
        return awsSesService.sendVerificationEmail(email, verificationCode);
    }

    public EmailVerificationTokenResponse verifyEmail(EmailVerificationRequest request) {
        if (!authService.isEmailAvailable(request.getEmail())) {
            throw new UserCredentialsException(ErrorStatus.EMAIL_ALREADY_EXISTS);
        }
        verificationCodeService.verifyCode(request.getEmail(), request.getCode());
        return emailVerificationTokenService.generateAndSaveCode(request.getEmail());
    }

    public JwtResponse signUp(String emailVerificationToken, SignUpRequest request) {
        if (!authService.isEmailAvailable(request.getEmail())) {
            throw new UserCredentialsException(ErrorStatus.EMAIL_ALREADY_EXISTS);
        }
        emailVerificationTokenService.verifyToken(request.getEmail(), emailVerificationToken);
        User user = authService.signUp(request);
        return jwtService.issueToken(user);
    }

    public JwtResponse signIn(SignInRequest request) {
        return authService.signIn(request);
    }

    public boolean isEmailAvailable(String email) {
        return authService.isEmailAvailable(email);
    }

    public JwtResponse reissueToken(String refreshToken) {
        return jwtService.reissueToken(refreshToken);
    }

}