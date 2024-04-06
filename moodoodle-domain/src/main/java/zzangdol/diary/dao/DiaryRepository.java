package zzangdol.diary.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zzangdol.diary.domain.Diary;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
}
