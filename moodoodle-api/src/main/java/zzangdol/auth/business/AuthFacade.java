package zzangdol.auth.business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import zzangdol.auth.implement.AuthService;
import zzangdol.auth.implement.EmailVerificationTokenService;
import zzangdol.auth.implement.VerificationCodeService;
import zzangdol.auth.presentation.dto.request.EmailVerificationRequest;
import zzangdol.auth.presentation.dto.request.RefreshTokenRequest;
import zzangdol.auth.presentation.dto.request.SignInRequest;
import zzangdol.auth.presentation.dto.request.SignUpRequest;
import zzangdol.auth.presentation.dto.request.SocialLoginRequest;
import zzangdol.auth.presentation.dto.response.EmailVerificationTokenResponse;
import zzangdol.constant.Constants;
import zzangdol.exception.custom.UserCredentialsException;
import zzangdol.jwt.JwtResponse;
import zzangdol.jwt.JwtService;
import zzangdol.response.status.ErrorStatus;
import zzangdol.scrap.implement.CategoryCommandService;
import zzangdol.scrap.presentation.dto.request.CategoryCreateRequest;
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
    private final CategoryCommandService categoryCommandService;

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
        categoryCommandService.createCategory(user, new CategoryCreateRequest(Constants.DEFAULT_CATEGORY_NAME));
        return jwtService.issueToken(user);
    }

    public JwtResponse signIn(SignInRequest request) {
        return authService.signIn(request);
    }

    public JwtResponse socialLogin(SocialLoginRequest request) {
        return authService.socialLogin(request);
    }

    public boolean isEmailAvailable(String email) {
        return authService.isEmailAvailable(email);
    }

    public JwtResponse reissueToken(RefreshTokenRequest request) {
        return jwtService.reissueToken(request.getRefreshToken());
    }

    public boolean signOut(RefreshTokenRequest request) {
        jwtService.invalidateToken(request.getRefreshToken());
        return true;
    }
}