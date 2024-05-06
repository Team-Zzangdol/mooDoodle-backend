package zzangdol.diary.dao.querydsl;

import static zzangdol.diary.domain.QDiary.diary;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import zzangdol.diary.domain.Diary;

@Repository
@RequiredArgsConstructor
public class DiaryQueryRepositoryImpl implements DiaryQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<Diary> findDiariesByUserAndYearAndMonth(Long userId, int year, int month) {
        return queryFactory.selectFrom(diary)
                .where(diary.user.id.eq(userId)
                        .and(diary.date.year().eq(year))
                        .and(diary.date.month().eq(month)))
                .orderBy(diary.date.asc())
                .fetch();
    }

}
