package zzangdol.report.implement;

import java.time.LocalDate;
import zzangdol.report.domain.Report;
import zzangdol.user.domain.User;

public interface ReportCommandService {

    Report createReport(User user);

    Report createReportByDate(User user, LocalDate startDate, LocalDate endDate);

    void markReportAsRead(User user, Long reportId);

}
