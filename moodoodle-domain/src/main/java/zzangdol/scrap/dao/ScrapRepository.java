package zzangdol.scrap.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import zzangdol.diary.domain.Diary;
import zzangdol.scrap.dao.querydsl.ScrapQueryRepository;
import zzangdol.scrap.domain.Scrap;
import zzangdol.user.domain.User;

public interface ScrapRepository extends JpaRepository<Scrap, Long>, ScrapQueryRepository {

    Optional<Scrap> findScrapByUserAndDiaryId(User user, Long diaryId);

    boolean existsByUserAndDiary(User user, Diary diary);

    void deleteByUser(User user);

}
