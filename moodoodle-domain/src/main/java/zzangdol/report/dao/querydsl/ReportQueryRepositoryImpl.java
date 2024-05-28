package zzangdol.report.dao.querydsl;

import static zzangdol.report.domain.QReport.report;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReportQueryRepositoryImpl implements ReportQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Long> findPrevReportId(Long userId, LocalDateTime createdAt) {
        Long reportId = queryFactory.select(report.id)
                .from(report)
                .where(report.user.id.eq(userId)
                        .and(report.createdAt.lt(createdAt)))
                .orderBy(report.createdAt.desc())
                .fetchFirst();

        return Optional.ofNullable(reportId);
    }

    @Override
    public Optional<Long> findNextReportId(Long userId, LocalDateTime createdAt) {
        Long reportId = queryFactory.select(report.id)
                .from(report)
                .where(report.user.id.eq(userId)
                        .and(report.createdAt.gt(createdAt)))
                .orderBy(report.createdAt.asc())
                .fetchFirst();
        return Optional.ofNullable(reportId);
    }

}
