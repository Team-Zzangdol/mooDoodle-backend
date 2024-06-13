package zzangdol.diary.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import zzangdol.diary.domain.Diary;
import zzangdol.diary.implement.DiaryCommandService;
import zzangdol.diary.implement.DiaryQueryService;
import zzangdol.diary.implement.ImageColorAnalyzer;
import zzangdol.diary.presentation.dto.request.DiaryCreateRequest;
import zzangdol.diary.presentation.dto.request.DiaryUpdateRequest;
import zzangdol.diary.presentation.dto.request.ImageCreateRequest;
import zzangdol.diary.presentation.dto.response.DiaryImageResponse;
import zzangdol.diary.presentation.dto.response.DiaryListResponse;
import zzangdol.diary.presentation.dto.response.DiaryResponse;
import zzangdol.diary.presentation.dto.response.ImageListResponse;
import zzangdol.emotion.dao.EmotionRepository;
import zzangdol.emotion.dao.querydsl.EmotionQueryRepository;
import zzangdol.emotion.domain.Emotion;
import zzangdol.feign.model.client.Text2ImageModelClient;
import zzangdol.feign.model.client.TextEmotionAnalysisModelClient;
import zzangdol.feign.model.dto.AudioResponse;
import zzangdol.feign.model.dto.ContentRequest;
import zzangdol.feign.model.dto.EmotionRequest;
import zzangdol.feign.model.dto.EmotionResponse;
import zzangdol.feign.model.dto.ImageResponse;
import zzangdol.scrap.implement.ScrapQueryService;
import zzangdol.user.domain.User;

@Slf4j
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
            EmotionResponse emotionResponse = textEmotionAnalysisModelClient.analyzeEmotion(
                    buildContentRequest(request.getContent()));
            List<String> emotionList = emotionResponse.getResult();
            emotions = emotionList.stream()
                    .map(emotionRepository::findByName)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
            log.info("generate emotion model success");
        } catch (Exception e) {
//            emotions = emotionQueryRepository.findRandomEmotions(3);

            List<String> emotionList = List.of("신남", "걱정스러움", "충만");
            emotions = emotionList.stream()
                    .map(emotionRepository::findByName)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());

            log.info("insert dummy data (emotion)");
        }

        String audioUrl;
        try {
            AudioResponse audioResponse = text2ImageModelClient.generateAudio(buildEmotionRequest(emotions.get(2)));
            audioUrl = audioResponse.getResult();
            log.info("audioUrl = {}", audioUrl);
            log.info("generate audio model success");
        } catch (Exception e) {
            audioUrl = "https://moodoodle-diary-image.s3.amazonaws.com/3990453f-a1e4-48a4-96a7-696b1d58a05c.wav";
            log.info("insert dummy data (audio)");
        }
//        String color = imageColorAnalyzer.analyzeAverageColorAsHex(request.getImageUrl());
        String color = "#FFFFFF";
        return diaryCommandService.createDiary(user, request, color, emotions, audioUrl).getId();
    }

    private ContentRequest buildContentRequest(String content) {
        return ContentRequest.builder()
                .content(content)
                .build();
    }

    private EmotionRequest buildEmotionRequest(Emotion emotion) {
        return EmotionRequest.builder()
//                .emotions(emotion.getName())
                .emotions("happy")
                .build();
    }

    public ImageListResponse generateDiaryImage(User user, ImageCreateRequest request) {
        List<String> imageUrls = new ArrayList<>();
        try {
            ImageResponse imageResponse = text2ImageModelClient.generateImages(
                    buildContentRequest(request.getContent()));
            imageUrls = imageResponse.getResult();
            log.info("generate image model success");
        } catch (Exception e) {
            imageUrls.add("https://moodoodle-diary-image.s3.amazonaws.com/68e6c7ab-817b-4221-aa8e-2939943618f9.png");
            imageUrls.add("https://moodoodle-diary-image.s3.amazonaws.com/0867f836-cf74-4a22-b278-4e86be8cc5c3.png");
            imageUrls.add("https://moodoodle-diary-image.s3.amazonaws.com/80914bc6-e454-4c35-89c5-caa6f3ba63fb.png");
            imageUrls.add("https://moodoodle-diary-image.s3.amazonaws.com/9156b631-4fd5-4506-a66e-f7580ce9d3df.png");
            log.info("insert dummy data (image)");
        }
        return DiaryMapper.toImageListResponse(imageUrls);
    }

    public DiaryImageResponse regenerateDiaryImage(User user, ImageCreateRequest request) {
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
