package zzangdol.moodoodleapi.member.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zzangdol.moodoodleapi.member.business.MemberFacade;
import zzangdol.moodoodleapi.member.presentation.dto.EmailVerificationRequest;
import zzangdol.moodoodlecommon.response.ApiResponse;

@RequiredArgsConstructor
@RequestMapping("/members")
@RestController
public class MemberController {

    private final MemberFacade memberFacade;

    @PostMapping("/send-verification-email")
    public ApiResponse<Boolean> sendVerificationEmail(@RequestParam String email) {
        return ApiResponse.onSuccess(memberFacade.sendVerificationEmail(email));
    }

    @PostMapping("/verify-email")
    public ApiResponse<Boolean> verifyEmail(@RequestBody EmailVerificationRequest request) {
        return ApiResponse.onSuccess(memberFacade.verifyEmail(request));
    }

}