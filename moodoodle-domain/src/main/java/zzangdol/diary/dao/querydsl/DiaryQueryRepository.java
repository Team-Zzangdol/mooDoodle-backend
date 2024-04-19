package zzangdol.diary.dao.querydsl;

import java.util.List;
import zzangdol.diary.domain.Diary;

public interface DiaryQueryRepository {

    List<Diary> findDiariesByUserAndMonth(Long userId, int year, int month);

}
