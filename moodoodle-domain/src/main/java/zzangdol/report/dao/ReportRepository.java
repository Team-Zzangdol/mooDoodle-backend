package zzangdol.report.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import zzangdol.report.domain.Report;
import zzangdol.user.domain.User;

public interface ReportRepository extends JpaRepository<Report, Long> {

    void deleteByUser(User user);

    Optional<Report> findFirstByUserOrderByCreatedAtDesc(User user);

}
