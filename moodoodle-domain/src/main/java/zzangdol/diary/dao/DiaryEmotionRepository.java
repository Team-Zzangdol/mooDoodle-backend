package zzangdol.diary.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zzangdol.diary.domain.DiaryEmotion;

public interface DiaryEmotionRepository extends JpaRepository<DiaryEmotion, Long> {
}
