package zzangdol.moodoodleapi.member.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zzangdol.moodoodleapi.email.VerificationCodeGenerator;
import zzangdol.moodoodleapi.email.VerificationCodeService;
import zzangdol.moodoodlecommon.response.ApiResponse;
import zzangdol.ses.service.AwsSesService;

@RequiredArgsConstructor
@RequestMapping("/members")
@RestController
public class MemberController {

    private final AwsSesService awsSesService;
    private final VerificationCodeGenerator verificationCodeGenerator;
    private final VerificationCodeService verificationCodeService;

    @PostMapping("/send-mail")
    public ApiResponse<Boolean> sendEmail(@RequestParam String email) {
        String verificationCode = verificationCodeGenerator.generate();
        verificationCodeService.saveVerificationCode(email, verificationCode, 300L);
        awsSesService.sendVerificationEmail(email, verificationCode);
        return ApiResponse.onSuccess(Boolean.TRUE);
    }

}
