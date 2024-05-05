package zzangdol.report.implement;

import zzangdol.report.domain.Report;
import zzangdol.user.domain.User;

public interface ReportCommandService {

    Report createReport(User user, int year, int month, int week);

}
