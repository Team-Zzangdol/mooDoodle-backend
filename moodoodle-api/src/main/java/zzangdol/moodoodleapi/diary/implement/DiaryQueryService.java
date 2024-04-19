package zzangdol.moodoodleapi.diary.implement;

import java.util.List;
import zzangdol.diary.domain.Diary;
import zzangdol.user.domain.User;

public interface DiaryQueryService {

    Diary getDiaryById(User user, Long diaryId);

    List<Diary> getMonthlyDiariesByUser(User user, int year, int month);

}
