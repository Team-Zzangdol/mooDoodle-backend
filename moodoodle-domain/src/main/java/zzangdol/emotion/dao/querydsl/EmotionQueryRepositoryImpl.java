package zzangdol.emotion.dao.querydsl;

import static zzangdol.emotion.domain.QEmotion.emotion;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import zzangdol.emotion.domain.Emotion;

@RequiredArgsConstructor
@Repository
public class EmotionQueryRepositoryImpl implements EmotionQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Emotion> findRandomEmotions(int limit) {
        return queryFactory.selectFrom(emotion)
                .orderBy(com.querydsl.core.types.dsl.Expressions.numberTemplate(Double.class, "function('RAND')").asc())
                .limit(limit)
                .fetch();
    }
}
