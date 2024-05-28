package zzangdol.diary.dao.querydsl;

import java.time.LocalDate;
import java.util.List;
import zzangdol.diary.domain.Diary;

public interface DiaryQueryRepository {

    List<Diary> findDiariesByUserAndYearAndMonth(Long userId, int year, int month);

    List<Diary> findDiariesByUserAndYearAndMonthAndWeek(Long userId, int year, int month, int week);

    List<Diary> findDiariesBetweenByUser(Long userId, LocalDate startDate, LocalDate endDate);

}
