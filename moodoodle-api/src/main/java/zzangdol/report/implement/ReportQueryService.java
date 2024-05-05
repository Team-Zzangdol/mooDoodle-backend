package zzangdol.report.implement;

import zzangdol.report.domain.Report;
import zzangdol.user.domain.User;

public interface ReportQueryService {

    Report getReportByUser(User user, Long reportId);

}