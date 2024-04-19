package zzangdol.diary.dao.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import zzangdol.diary.domain.Diary;
import zzangdol.diary.domain.QDiary;

@Repository
@RequiredArgsConstructor
public class DiaryQueryRepositoryImpl implements DiaryQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<Diary> findDiariesByUserAndMonth(Long userId, int year, int month) {
        QDiary qDiary = QDiary.diary;

        return queryFactory.selectFrom(qDiary)
                .where(qDiary.user.id.eq(userId)
                        .and(qDiary.date.year().eq(year))
                        .and(qDiary.date.month().eq(month)))
                .orderBy(qDiary.date.asc())
                .fetch();
    }

}
