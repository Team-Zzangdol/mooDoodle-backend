package zzangdol.moodoodleapi.diary.business;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import zzangdol.emotion.domain.Emotion;
import zzangdol.moodoodleapi.diary.implement.DiaryCommandService;
import zzangdol.moodoodleapi.diary.presentation.dto.request.DiaryCreateRequest;
import zzangdol.user.domain.User;

@Component
@RequiredArgsConstructor
public class DiaryFacade {

    private final DiaryCommandService diaryCommandService;
//    private final TextEmotionAnalysisModelClient textEmotionAnalysisModelClient;
//    private final ImageColorAnalyzer imageColorAnalyzer;

    public Long createDiary(User user, DiaryCreateRequest request) {
        // TODO List<Emotion> emotions = textEmotionAnalysisModelClient.analyzeEmotion(request.getContent());
        List<Emotion> emotions = new ArrayList<>();

        // TODO String color = imageColorAnalyzer.analyzeColor(request.getImageUrl());
        String color = "";
        return diaryCommandService.createDiary(user, request, color, emotions).getId();
    }

}
