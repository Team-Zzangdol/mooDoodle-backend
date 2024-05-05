package zzangdol.report.presentation;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zzangdol.global.annotation.AuthUser;
import zzangdol.report.business.ReportFacade;
import zzangdol.response.ResponseDto;
import zzangdol.user.domain.User;

@RequiredArgsConstructor
@ApiResponse(responseCode = "2000", description = "성공")
@Tag(name = "4️⃣ Report API", description = "리포트 API")
@RequestMapping("/api/reports")
@RestController
public class ReportController {

    private final ReportFacade reportFacade;

    @PostMapping
    public ResponseDto<Long> createReport(
            @AuthUser User user,
            @RequestParam int year,
            @RequestParam int month,
            @RequestParam int week) {
        return ResponseDto.onSuccess(reportFacade.createReport(user, year, month, week));
    }

}
