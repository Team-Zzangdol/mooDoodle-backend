package zzangdol.moodoodleapi.auth.presentation;

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
import zzangdol.moodoodlecommon.response.ApiResponse;

@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {

    private final AuthFacade authFacade;

    @PostMapping("/send-verification-email")
    public ApiResponse<Boolean> sendVerificationEmail(@RequestParam String email) {
        return ApiResponse.onSuccess(authFacade.sendVerificationEmail(email));
    }

    @PostMapping("/verify-email")
    public ApiResponse<String> verifyEmail(@RequestBody EmailVerificationRequest request) {
        return ApiResponse.onSuccess(authFacade.verifyEmail(request));
    }

    @PostMapping("/sign-up")
    public ApiResponse<JwtResponse> signUp(@RequestHeader("X-Email-Verification-Token") String emailVerificationToken,
                                           @RequestBody SignUpRequest request) {
        return ApiResponse.onSuccess(authFacade.signUp(emailVerificationToken, request));
    }

    @PostMapping("/sign-in")
    public ApiResponse<JwtResponse> signUp(@RequestBody SignInRequest request) {
        return ApiResponse.onSuccess(authFacade.signIn(request));
    }

    @GetMapping("/check-email")
    public ApiResponse<Boolean> checkEmailAvailability(@RequestParam String email) {
        return ApiResponse.onSuccess(authFacade.isEmailAvailable(email));
    }

    @PostMapping("/reissue")
    public ApiResponse<JwtResponse> reissue(@RequestParam String refreshToken) {
        return ApiResponse.onSuccess(authFacade.reissueToken(refreshToken));
    }

}