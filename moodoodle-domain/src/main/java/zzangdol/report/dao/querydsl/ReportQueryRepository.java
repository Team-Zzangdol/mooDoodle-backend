package zzangdol.report.dao.querydsl;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ReportQueryRepository {

    Optional<Long> findPrevReportId(Long userId, LocalDateTime createdAt);

    Optional<Long> findNextReportId(Long userId, LocalDateTime createdAt);

}
