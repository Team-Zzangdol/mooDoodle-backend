package zzangdol.report.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zzangdol.global.annotation.ApiErrorCodeExample;
import zzangdol.global.annotation.AuthUser;
import zzangdol.report.business.ReportFacade;
import zzangdol.report.presentation.dto.response.LatestReportResponse;
import zzangdol.report.presentation.dto.response.ReportResponse;
import zzangdol.response.ResponseDto;
import zzangdol.response.status.ErrorStatus;
import zzangdol.user.domain.User;

@RequiredArgsConstructor
@ApiResponse(responseCode = "2000", description = "성공")
@Tag(name = "6️⃣ Report API", description = "리포트 API")
@RequestMapping("/api/reports")
@RestController
public class ReportController {

    private final ReportFacade reportFacade;

    @ApiErrorCodeExample({
            ErrorStatus.DIARY_NOT_FOUND,
            ErrorStatus.REPORT_EMOTION_DATA_MISSING,
            ErrorStatus.INTERNAL_SERVER_ERROR
    })
    @Operation(
            summary = "[테스트] 리포트 생성 🔑",
            description = "새로운 리포트를 생성합니다."
    )
    @PostMapping
    public ResponseDto<Long> createReport(
            @AuthUser User user,
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate) {
        return ResponseDto.onSuccess(reportFacade.createReportByDate(user, startDate, endDate));
    }

    @ApiErrorCodeExample({
            ErrorStatus.REPORT_NOT_FOUND,
            ErrorStatus.REPORT_ACCESS_DENIED,
            ErrorStatus.INTERNAL_SERVER_ERROR
    })
    @Operation(
            summary = "리포트 단건 조회 🔑",
            description = "지정된 ID의 리포트를 조회하고 상세 정보를 반환합니다. 해당 리포트가 가장 최근 리포트면 nextReportId = -1, 가장 오래된 리포트면 prevReport = -1를 반환합니다."
    )
    @GetMapping("/{reportId}")
    public ResponseDto<ReportResponse> getReportByUser(@AuthUser User user,
                                                       @PathVariable("reportId") Long reportId) {
        return ResponseDto.onSuccess(reportFacade.getReportByUser(user, reportId));
    }

    @ApiErrorCodeExample({
            ErrorStatus.REPORT_NOT_FOUND,
            ErrorStatus.INTERNAL_SERVER_ERROR
    })
    @Operation(
            summary = "최신 리포트 상태 조회 🔑",
            description = "최신 리포트의 읽었는지 여부와 ID를 반환합니다. 생성된 리포트가 없으면 4150 에러를 반환합니다."
    )
    @GetMapping("/latest")
    public ResponseDto<LatestReportResponse> getLatestReportStatus(@AuthUser User user) {
        return ResponseDto.onSuccess(reportFacade.getLatestReportStatus(user));
    }

}