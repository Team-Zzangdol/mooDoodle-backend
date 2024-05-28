package zzangdol.report.implement;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zzangdol.exception.custom.ReportAccessDeniedException;
import zzangdol.exception.custom.ReportNotFoundException;
import zzangdol.report.dao.ReportRepository;
import zzangdol.report.domain.Report;
import zzangdol.user.domain.User;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReportQueryServiceImpl implements ReportQueryService {

    private final ReportRepository reportRepository;

    @Override
    public Report getReportByUser(User user, Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> ReportNotFoundException.EXCEPTION);
        checkReportOwnership(user, report);
        return report;
    }

    @Override
    public Report getLatestReportByUser(User user) {
        return reportRepository.findFirstByUserOrderByCreatedAtDesc(user)
                .orElseThrow(() -> ReportNotFoundException.EXCEPTION);
    }

    @Override
    public Long getPreviousReportId(User user, LocalDateTime createdAt) {
        return reportRepository.findPrevReportId(user.getId(), createdAt).orElse(-1L);
    }

    @Override
    public Long getNextReportId(User user, LocalDateTime createdAt) {
        return reportRepository.findNextReportId(user.getId(), createdAt).orElse(-1L);
    }

    private void checkReportOwnership(User user, Report report) {
        if (!report.getUser().getId().equals(user.getId())) {
            throw ReportAccessDeniedException.EXCEPTION;
        }
    }

}
