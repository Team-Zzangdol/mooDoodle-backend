package zzangdol.emotion.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import zzangdol.emotion.domain.Emotion;

public interface EmotionRepository extends JpaRepository<Emotion, Long> {

    Optional<Emotion> findByName(String name);

}
