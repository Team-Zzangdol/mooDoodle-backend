package zzangdol.auth.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zzangdol.auth.business.AuthFacade;
import zzangdol.auth.presentation.dto.request.EmailVerificationRequest;
import zzangdol.auth.presentation.dto.request.SignInRequest;
import zzangdol.auth.presentation.dto.request.SignUpRequest;
import zzangdol.auth.presentation.dto.response.EmailVerificationTokenResponse;
import zzangdol.global.annotation.ApiErrorCodeExample;
import zzangdol.jwt.JwtResponse;
import zzangdol.response.ResponseDto;
import zzangdol.response.status.ErrorStatus;

@RequiredArgsConstructor
@ApiResponse(responseCode = "2000", description = "성공")
@Tag(name = "1️⃣ Auth API", description = "인증 API")
@RequestMapping("/api/auth")
@RestController
public class AuthController {

    private final AuthFacade authFacade;

    @ApiErrorCodeExample({
            ErrorStatus.EMAIL_ALREADY_EXISTS,
            ErrorStatus.INTERNAL_SERVER_ERROR
    })
    @Operation(summary = "인증 이메일 발송", description = "지정된 이메일 주소로 인증 이메일을 발송합니다.")
    @PostMapping("/send-verification-email")
    public ResponseDto<Boolean> sendVerificationEmail(@RequestParam("email") String email) {
        return ResponseDto.onSuccess(authFacade.sendVerificationEmail(email));
    }

    @ApiErrorCodeExample({
            ErrorStatus.TOKEN_INVALID,
            ErrorStatus.TOKEN_EXPIRED,
            ErrorStatus.EMAIL_ALREADY_EXISTS,
            ErrorStatus.INTERNAL_SERVER_ERROR
    })
    @Operation(summary = "이메일 검증", description = "제공된 검증 코드로 사용자의 이메일을 검증합니다.")
    @PostMapping("/verify-email")
    public ResponseDto<EmailVerificationTokenResponse> verifyEmail(@RequestBody EmailVerificationRequest request) {
        return ResponseDto.onSuccess(authFacade.verifyEmail(request));
    }

    @ApiErrorCodeExample({
            ErrorStatus.TOKEN_INVALID,
            ErrorStatus.USER_NOT_FOUND,
            ErrorStatus.EMAIL_ALREADY_EXISTS,
            ErrorStatus.INTERNAL_SERVER_ERROR
    })
    @Operation(summary = "사용자 회원가입", description = "이메일 검증 토큰을 사용하여 새로운 사용자를 등록합니다.")
    @PostMapping("/sign-up")
    public ResponseDto<JwtResponse> signUp(@RequestHeader("X-Email-Verification-Token") String emailVerificationToken,
                                           @RequestBody SignUpRequest request) {
        return ResponseDto.onSuccess(authFacade.signUp(emailVerificationToken, request));
    }

    @ApiErrorCodeExample({
            ErrorStatus.PASSWORD_MISMATCH,
            ErrorStatus.USER_NOT_FOUND,
            ErrorStatus.INTERNAL_SERVER_ERROR
    })
    @Operation(summary = "사용자 로그인", description = "사용자를 인증하고 Access Token, Refresh Token을 발급합니다.")
    @PostMapping("/sign-in")
    public ResponseDto<JwtResponse> signUp(@RequestBody SignInRequest request) {
        return ResponseDto.onSuccess(authFacade.signIn(request));
    }

    @ApiErrorCodeExample({
            ErrorStatus.USER_NOT_FOUND
    })
    @Operation(summary = "이메일 사용 가능 여부 확인", description = "제공된 이메일이 등록에 사용 가능한지 확인합니다.")
    @GetMapping("/check-email")
    public ResponseDto<Boolean> checkEmailAvailability(@RequestParam String email) {
        return ResponseDto.onSuccess(authFacade.isEmailAvailable(email));
    }

    @ApiErrorCodeExample({
            ErrorStatus.REFRESH_TOKEN_NOT_FOUND,
            ErrorStatus.TOKEN_INVALID,
            ErrorStatus.TOKEN_EXPIRED,
            ErrorStatus.TOKEN_UNSUPPORTED,
            ErrorStatus.TOKEN_CLAIMS_EMPTY,
            ErrorStatus.USER_NOT_FOUND,
            ErrorStatus.INTERNAL_SERVER_ERROR
    })
    @Operation(summary = "토큰 재발급", description = "Refresh Token, Access Token을 재발급합니다.")
    @PostMapping("/reissue")
    public ResponseDto<JwtResponse> reissue(@RequestParam String refreshToken) {
        return ResponseDto.onSuccess(authFacade.reissueToken(refreshToken));
    }

}