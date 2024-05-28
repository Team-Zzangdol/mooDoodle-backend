package zzangdol.report.business;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import zzangdol.report.implement.ReportCommandService;
import zzangdol.report.implement.ReportQueryService;
import zzangdol.report.presentation.dto.response.ReportResponse;
import zzangdol.user.domain.User;

@RequiredArgsConstructor
@Component
public class ReportFacade {

    private final ReportCommandService reportCommandService;
    private final ReportQueryService reportQueryService;

    public Long createReportByDate(User user, LocalDate startDate, LocalDate endDate) {
        return reportCommandService.createReportByDate(user, startDate, endDate).getId();
    }

    public ReportResponse getReportByUser(User user, Long reportId) {
        return ReportMapper.toReportResponse(reportQueryService.getReportByUser(user, reportId));
    }


}
