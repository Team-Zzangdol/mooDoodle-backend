package zzangdol.diary.dao;

import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import zzangdol.diary.domain.Diary;
import zzangdol.user.domain.User;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

    boolean existsByDateAndUserId(LocalDateTime date, Long userId);

    void deleteByUser(User user);

}
