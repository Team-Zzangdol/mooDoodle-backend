package zzangdol.diary.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import zzangdol.diary.domain.Diary;
import zzangdol.diary.implement.DiaryCommandService;
import zzangdol.diary.implement.DiaryQueryService;
import zzangdol.diary.implement.ImageColorAnalyzer;
import zzangdol.diary.presentation.dto.request.DiaryCreateRequest;
import zzangdol.diary.presentation.dto.request.DiaryUpdateRequest;
import zzangdol.diary.presentation.dto.request.ImageCreateRequest;
import zzangdol.diary.presentation.dto.response.DiaryListResponse;
import zzangdol.diary.presentation.dto.response.DiaryResponse;
import zzangdol.diary.presentation.dto.response.ImageListResponse;
import zzangdol.diary.presentation.dto.response.ImageResponse;
import zzangdol.emotion.dao.EmotionRepository;
import zzangdol.emotion.dao.querydsl.EmotionQueryRepository;
import zzangdol.emotion.domain.Emotion;
import zzangdol.feign.model.client.Text2ImageModelClient;
import zzangdol.feign.model.client.TextEmotionAnalysisModelClient;
import zzangdol.feign.model.dto.AudioResponse;
import zzangdol.feign.model.dto.ContentRequest;
import zzangdol.feign.model.dto.EmotionResponse;
import zzangdol.scrap.implement.ScrapQueryService;
import zzangdol.user.domain.User;

@RequiredArgsConstructor
@Component
public class DiaryFacade {

    private final DiaryCommandService diaryCommandService;
    private final DiaryQueryService diaryQueryService;
    private final ScrapQueryService scrapQueryService;
    private final EmotionQueryRepository emotionQueryRepository;    // TODO 구현 후 제거
    private final EmotionRepository emotionRepository;
    private final TextEmotionAnalysisModelClient textEmotionAnalysisModelClient;
    private final Text2ImageModelClient text2ImageModelClient;
    private final ImageColorAnalyzer imageColorAnalyzer;

    public Long createDiary(User user, DiaryCreateRequest request) {
        List<Emotion> emotions;
        try {
            EmotionResponse emotionResponse = textEmotionAnalysisModelClient.analyzeEmotion(buildContentRequest(request));
            List<String> emotionList = emotionResponse.getResult();
            emotions = emotionList.stream()
                    .map(emotionRepository::findByName)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            emotions = emotionQueryRepository.findRandomEmotions(3);
        }

        String audioUrl;
        try {
            AudioResponse audioResponse = text2ImageModelClient.generateAudios(buildContentRequest(request));
            audioUrl = audioResponse.getResult();
        } catch (Exception e) {
            audioUrl = "https://moodoodle-diary-image.s3.ap-northeast-2.amazonaws.com/happyday_0.wav";
        }
        String color = imageColorAnalyzer.analyzeAverageColorAsHex(request.getImageUrl());
        return diaryCommandService.createDiary(user, request, color, emotions, audioUrl).getId();
    }

    private ContentRequest buildContentRequest(DiaryCreateRequest request) {
        return ContentRequest.builder()
                .content(request.getContent())
                .build();
    }

    public ImageListResponse generateDiaryImage(User user, ImageCreateRequest request) {
//        List<String> imageUrls = text2ImageModelClient.generateImage(request.getContent());
        List<String> imageUrls = new ArrayList<>();
        imageUrls.add("https://moodoodle-diary-image.s3.ap-northeast-2.amazonaws.com/diary_image_1.png");
        imageUrls.add("https://moodoodle-diary-image.s3.ap-northeast-2.amazonaws.com/diary_image_2.png");
        imageUrls.add("https://moodoodle-diary-image.s3.ap-northeast-2.amazonaws.com/diary_image_3.png");
        imageUrls.add("https://moodoodle-diary-image.s3.ap-northeast-2.amazonaws.com/diary_image_4.png");
        return DiaryMapper.toImageListResponse(imageUrls);
    }

    public ImageResponse regenerateDiaryImage(User user, ImageCreateRequest request) {
//        String imageUrl = text2ImageModelClient.regenerateImage(request.getContent());
        return DiaryMapper.toImageResponse(
                "https://moodoodle-diary-image.s3.ap-northeast-2.amazonaws.com/diary_image_1.png");
    }

    public Long updateDiary(User user, Long diaryId, DiaryUpdateRequest request) {
        return diaryCommandService.updateDiary(user, diaryId, request).getId();
    }

    public void deleteDiary(User user, Long diaryId) {
        diaryCommandService.deleteDiary(user, diaryId);
    }

    public DiaryResponse getDiaryByUser(User user, Long diaryId) {
        Diary diary = diaryQueryService.getDiaryByUser(user, diaryId);
        boolean isScrapped = scrapQueryService.isDiaryScrappedByUser(user, diaryId);
        return DiaryMapper.toDiaryResponse(diary, isScrapped);
    }

    public DiaryListResponse getMonthlyDiariesByUser(User user, int year, int month) {
        return DiaryMapper.toDiaryListResponse(diaryQueryService.getMonthlyDiariesByUser(user, year, month), year,
                month);
    }

}
