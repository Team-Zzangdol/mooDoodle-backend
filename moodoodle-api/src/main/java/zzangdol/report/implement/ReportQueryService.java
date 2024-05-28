package zzangdol.report.implement;

import java.time.LocalDateTime;
import zzangdol.report.domain.Report;
import zzangdol.user.domain.User;

public interface ReportQueryService {

    Report getReportByUser(User user, Long reportId);

    Report getLatestReportByUser(User user);

    Long getPreviousReportId(User user, LocalDateTime createdAt);

    Long getNextReportId(User user, LocalDateTime createdAt);


}