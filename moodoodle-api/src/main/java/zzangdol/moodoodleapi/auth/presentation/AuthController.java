package zzangdol.moodoodleapi.auth.presentation;

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
import zzangdol.moodoodleapi.auth.business.AuthFacade;
import zzangdol.moodoodleapi.auth.presentation.dto.request.EmailVerificationRequest;
import zzangdol.moodoodleapi.auth.presentation.dto.request.SignInRequest;
import zzangdol.moodoodleapi.auth.presentation.dto.request.SignUpRequest;
import zzangdol.moodoodleapi.jwt.JwtResponse;
import zzangdol.moodoodlecommon.response.ResponseDto;

@RequiredArgsConstructor

@ApiResponse(responseCode = "2000", description = "성공")
@Tag(name = "Auth API", description = "인증 API")
@RequestMapping("/api/auth")
@RestController
public class AuthController {

    private final AuthFacade authFacade;

    @PostMapping("/send-verification-email")
    public ResponseDto<Boolean> sendVerificationEmail(@RequestParam String email) {
        return ResponseDto.onSuccess(authFacade.sendVerificationEmail(email));
    }

    @PostMapping("/verify-email")
    public ResponseDto<String> verifyEmail(@RequestBody EmailVerificationRequest request) {
        return ResponseDto.onSuccess(authFacade.verifyEmail(request));
    }

    @PostMapping("/sign-up")
    public ResponseDto<JwtResponse> signUp(@RequestHeader("X-Email-Verification-Token") String emailVerificationToken,
                                           @RequestBody SignUpRequest request) {
        return ResponseDto.onSuccess(authFacade.signUp(emailVerificationToken, request));
    }

    @PostMapping("/sign-in")
    public ResponseDto<JwtResponse> signUp(@RequestBody SignInRequest request) {
        return ResponseDto.onSuccess(authFacade.signIn(request));
    }

    @GetMapping("/check-email")
    public ResponseDto<Boolean> checkEmailAvailability(@RequestParam String email) {
        return ResponseDto.onSuccess(authFacade.isEmailAvailable(email));
    }

    @PostMapping("/reissue")
    public ResponseDto<JwtResponse> reissue(@RequestParam String refreshToken) {
        return ResponseDto.onSuccess(authFacade.reissueToken(refreshToken));
    }

}