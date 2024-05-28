package zzangdol.diary.dao;

import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import zzangdol.diary.dao.querydsl.DiaryQueryRepository;
import zzangdol.diary.domain.Diary;
import zzangdol.user.domain.User;

public interface DiaryRepository extends JpaRepository<Diary, Long>, DiaryQueryRepository {

    boolean existsByDateAndUserId(LocalDate date, Long userId);

    boolean existsByDateAndUserIdAndIdNot(LocalDate date, Long userId, Long id);

    void deleteByUser(User user);

}
