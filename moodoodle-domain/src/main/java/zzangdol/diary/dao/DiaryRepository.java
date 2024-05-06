package zzangdol.diary.dao;

import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import zzangdol.diary.domain.Diary;
import zzangdol.user.domain.User;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

    boolean existsByDateAndUserId(LocalDate date, Long userId);

    boolean existsByDateAndUserIdAndIdNot(LocalDate date, Long userId, Long id);

    void deleteByUser(User user);

}
