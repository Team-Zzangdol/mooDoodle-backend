package zzangdol.emotion.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zzangdol.emotion.domain.Emotion;

public interface EmotionRepository extends JpaRepository<Emotion, Long> {
}
