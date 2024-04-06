package zzangdol.report.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zzangdol.report.domain.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
