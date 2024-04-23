package zzangdol.report.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zzangdol.report.domain.Report;
import zzangdol.user.domain.User;

public interface ReportRepository extends JpaRepository<Report, Long> {

    void deleteByUser(User user);

}
