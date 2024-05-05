package zzangdol.report.business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import zzangdol.report.implement.ReportCommandService;
import zzangdol.user.domain.User;

@RequiredArgsConstructor
@Component
public class ReportFacade {

    private final ReportCommandService reportCommandService;

    public Long createReport(User user, int year, int month, int week) {
        return reportCommandService.createReport(user, year, month, week).getId();
    }

}
