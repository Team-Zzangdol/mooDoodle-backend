package zzangdol.diary.dao.querydsl;

import static zzangdol.diary.domain.QDiary.diary;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import zzangdol.diary.domain.Diary;

@Slf4j
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
        LocalDate weekStartDate = getStartDateOfWeek(year, month, week);
        LocalDate weekEndDate = weekStartDate.plusDays(6);

        return queryFactory
                .selectFrom(diary)
                .where(diary.user.id.eq(userId)
                        .and(diary.date.between(weekStartDate, weekEndDate)))
                .fetch();
    }

    @Override
    public List<Diary> findDiariesBetweenByUser(Long userId, LocalDate startDate, LocalDate endDate) {
        return queryFactory.selectFrom(diary)
                .where(diary.user.id.eq(userId)
                        .and(diary.date.between(startDate, endDate)))
                .fetch();
    }

    public static LocalDate getStartDateOfWeek(int year, int month, int weekOfMonth) {
        Calendar calendar = Calendar.getInstance(Locale.KOREA);
        calendar.clear();

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(4);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.THURSDAY) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }

        calendar.add(Calendar.WEEK_OF_MONTH, weekOfMonth - 1);

        return LocalDate.ofInstant(calendar.toInstant(), ZoneId.systemDefault());
    }

}
