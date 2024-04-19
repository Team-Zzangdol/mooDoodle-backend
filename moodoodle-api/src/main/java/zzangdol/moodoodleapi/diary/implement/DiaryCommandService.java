package zzangdol.moodoodleapi.diary.implement;

import java.util.List;
import zzangdol.diary.domain.Diary;
import zzangdol.emotion.domain.Emotion;
import zzangdol.moodoodleapi.diary.presentation.dto.request.DiaryCreateRequest;
import zzangdol.moodoodleapi.diary.presentation.dto.request.DiaryUpdateRequest;
import zzangdol.user.domain.User;

public interface DiaryCommandService {

    Diary createDiary(User user, DiaryCreateRequest request, String color, List<Emotion> emotions);

    Diary updateDiary(User user, Long diaryId, DiaryUpdateRequest request);

    void deleteDiary(User user, Long diaryId);

}