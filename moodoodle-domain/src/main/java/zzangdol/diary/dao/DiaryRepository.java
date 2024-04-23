package zzangdol.diary.dao;

import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import zzangdol.diary.domain.Diary;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

    boolean existsByDateAndUserId(LocalDateTime date, Long userId);

}
