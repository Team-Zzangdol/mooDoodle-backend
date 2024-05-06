package zzangdol.diary.dao.querydsl;

import static zzangdol.diary.domain.QDiary.diary;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import zzangdol.diary.domain.Diary;

@Repository
@RequiredArgsConstructor
public class DiaryQueryRepositoryImpl implements DiaryQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Diary> findDiariesByUserAndYearAndMonth(Long userId, int year, int month) {
        return queryFactory.selectFrom(diary)
                .where(diary.user.id.eq(userId)
                        .and(diary.date.year().eq(year))
                        .and(diary.date.month().eq(month)))
                .orderBy(diary.date.asc())
                .fetch();
    }

    @Override
    public List<Diary> findDiariesByUserAndYearAndMonthAndWeek(Long userId, int year, int month, int week) {
        QDiary diary = QDiary.diary;

        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        LocalDate firstSunday = firstDayOfMonth.with(DayOfWeek.SUNDAY);
        LocalDate weekStartDate = firstSunday.plusWeeks(week - 1);
        LocalDate weekEndDate = weekStartDate.plusWeeks(1);

        LocalDateTime startDateTime = weekStartDate.atStartOfDay();
        LocalDateTime endDateTime = weekEndDate.atStartOfDay();

        return queryFactory
                .selectFrom(diary)
                .where(diary.user.id.eq(userId)
                        .and(diary.date.between(startDateTime, endDateTime)))
                .fetch();
    }

}
