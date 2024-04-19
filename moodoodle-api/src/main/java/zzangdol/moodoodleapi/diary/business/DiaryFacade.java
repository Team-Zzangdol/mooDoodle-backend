package zzangdol.moodoodleapi.diary.business;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import zzangdol.emotion.domain.Emotion;
import zzangdol.moodoodleapi.diary.implement.DiaryCommandService;
import zzangdol.moodoodleapi.diary.implement.DiaryQueryService;
import zzangdol.moodoodleapi.diary.presentation.dto.request.DiaryCreateRequest;
import zzangdol.moodoodleapi.diary.presentation.dto.request.DiaryUpdateRequest;
import zzangdol.moodoodleapi.diary.presentation.dto.response.DiaryDetailResponse;
import zzangdol.user.domain.User;

@Component
@RequiredArgsConstructor
public class DiaryFacade {

    private final DiaryCommandService diaryCommandService;
    private final DiaryQueryService diaryQueryService;
//    private final TextEmotionAnalysisModelClient textEmotionAnalysisModelClient;
//    private final ImageColorAnalyzer imageColorAnalyzer;

    public Long createDiary(User user, DiaryCreateRequest request) {
        // TODO List<Emotion> emotions = textEmotionAnalysisModelClient.analyzeEmotion(request.getContent());
        List<Emotion> emotions = new ArrayList<>();

        // TODO String color = imageColorAnalyzer.analyzeColor(request.getImageUrl());
        String color = "";
        return diaryCommandService.createDiary(user, request, color, emotions).getId();
    }

    public Long updateDiary(User user, Long diaryId, DiaryUpdateRequest request) {
        return diaryCommandService.updateDiary(user, diaryId, request).getId();
    }

    public void deleteDiary(User user, Long diaryId) {
        diaryCommandService.deleteDiary(user, diaryId);
    }

    public DiaryDetailResponse getDiaryById(User user, Long diaryId) {
        return DiaryMapper.toDiaryDetailResponse(diaryQueryService.getDiaryById(user, diaryId));
    }

}
