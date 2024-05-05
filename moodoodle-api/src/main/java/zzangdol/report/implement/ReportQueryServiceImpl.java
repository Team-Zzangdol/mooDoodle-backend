package zzangdol.report.implement;

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

    private void checkReportOwnership(User user, Report report) {
        if (!report.getUser().getId().equals(user.getId())) {
            throw ReportAccessDeniedException.EXCEPTION;
        }
    }

}
