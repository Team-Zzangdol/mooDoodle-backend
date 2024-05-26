package zzangdol.emotion.dao.querydsl;

import java.util.List;
import zzangdol.emotion.domain.Emotion;

public interface EmotionQueryRepository {

    List<Emotion> findRandomEmotions(int limit);

}
