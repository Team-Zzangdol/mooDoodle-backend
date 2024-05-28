package zzangdol.report.business;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import zzangdol.report.domain.Report;
import zzangdol.report.implement.ReportCommandService;
import zzangdol.report.implement.ReportQueryService;
import zzangdol.report.presentation.dto.response.LatestReportResponse;
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
        Report report = reportQueryService.getReportByUser(user, reportId);
        reportCommandService.markReportAsRead(user, report.getId());
        Long prevReportId = reportQueryService.getPreviousReportId(user, report.getCreatedAt());
        Long nextReportId = reportQueryService.getNextReportId(user, report.getCreatedAt());
        return ReportMapper.toReportResponse(report, prevReportId, nextReportId);
    }


    public LatestReportResponse getLatestReportStatus(User user) {
        Report report = reportQueryService.getLatestReportByUser(user);
        return ReportMapper.toLatestReportResponse(report, user.getIsRead());
    }
}
