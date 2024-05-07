package zzangdol.diary.business;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import zzangdol.diary.implement.DiaryCommandService;
import zzangdol.diary.implement.DiaryQueryService;
import zzangdol.diary.implement.ImageColorAnalyzer;
import zzangdol.diary.presentation.dto.request.DiaryCreateRequest;
import zzangdol.diary.presentation.dto.request.DiaryUpdateRequest;
import zzangdol.diary.presentation.dto.request.ImageCreateRequest;
import zzangdol.diary.presentation.dto.response.DiaryListResponse;
import zzangdol.diary.presentation.dto.response.DiaryResponse;
import zzangdol.diary.presentation.dto.response.ImageListResponse;
import zzangdol.emotion.domain.Emotion;
import zzangdol.user.domain.User;

@RequiredArgsConstructor
@Component
public class DiaryFacade {

    private final DiaryCommandService diaryCommandService;
    private final DiaryQueryService diaryQueryService;
//    private final TextEmotionAnalysisModelClient textEmotionAnalysisModelClient;
//    private final Text2ImageModelClient text2ImageModelClient;
    private final ImageColorAnalyzer imageColorAnalyzer;

    public Long createDiary(User user, DiaryCreateRequest request) {
        // TODO List<Emotion> emotions = textEmotionAnalysisModelClient.analyzeEmotion(request.getContent());
        List<Emotion> emotions = new ArrayList<>();
         String color = imageColorAnalyzer.analyzeAverageColorAsHex(request.getImageUrl());
        return diaryCommandService.createDiary(user, request, color, emotions).getId();
    }

    public ImageListResponse createDiaryImage(User user, ImageCreateRequest request) {
//        List<String> imageUrls = text2ImageModelClient.generateImage(request.getContent());
        List<String> imageUrls = new ArrayList<>();
        return DiaryMapper.toImageResponse(imageUrls);
    }

    public Long updateDiary(User user, Long diaryId, DiaryUpdateRequest request) {
        return diaryCommandService.updateDiary(user, diaryId, request).getId();
    }

    public void deleteDiary(User user, Long diaryId) {
        diaryCommandService.deleteDiary(user, diaryId);
    }

    public DiaryResponse getDiaryByUser(User user, Long diaryId) {
        return DiaryMapper.toDiaryResponse(diaryQueryService.getDiaryByUser(user, diaryId));
    }

    public DiaryListResponse getMonthlyDiariesByUser(User user, int year, int month) {
        return DiaryMapper.toDiaryListResponse(diaryQueryService.getMonthlyDiariesByUser(user, year, month), year, month);
    }

}
