package zzangdol.report.implement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zzangdol.diary.dao.querydsl.DiaryQueryRepository;
import zzangdol.diary.domain.Diary;
import zzangdol.emotion.domain.Emotion;
import zzangdol.emotion.domain.EmotionPolarity;
import zzangdol.exception.custom.DiaryNotFoundException;
import zzangdol.exception.custom.ReportEmotionDataMissingException;
import zzangdol.report.dao.ReportRepository;
import zzangdol.report.dao.querydsl.AssetQueryRepository;
import zzangdol.report.domain.Asset;
import zzangdol.report.domain.Report;
import zzangdol.report.domain.ReportEmotion;
import zzangdol.user.domain.User;

@RequiredArgsConstructor
@Transactional
@Service
public class ReportCommandServiceImpl implements ReportCommandService {

    private final ReportRepository reportRepository;
    private final DiaryQueryRepository diaryQueryRepository;
    private final AssetQueryRepository assetQueryRepository;


    @Override
    public Report createReport(User user, int year, int month, int week) {
        List<Diary> diaries = diaryQueryRepository.findDiariesByUserAndYearAndMonthAndWeek(user.getId(), year, month,
                week);
        if (diaries.isEmpty()) {
            throw DiaryNotFoundException.EXCEPTION;
        }

        Map<Emotion, Integer> emotionCounts = new HashMap<>();
        AtomicInteger positiveCount = new AtomicInteger(0);
        AtomicInteger negativeCount = new AtomicInteger(0);

        for (Diary diary : diaries) {
            diary.getDiaryEmotions().forEach(diaryEmotion -> {
                Emotion emotion = diaryEmotion.getEmotion();
                emotionCounts.merge(emotion, 1, Integer::sum);
                if (emotion.getPolarity() == EmotionPolarity.POSITIVE) {
                    positiveCount.getAndIncrement();
                } else if (emotion.getPolarity() == EmotionPolarity.NEGATIVE) {
                    negativeCount.getAndIncrement();
                }
            });
        }

        int totalEmotions = positiveCount.get() + negativeCount.get();
        if (totalEmotions == 0) {
            throw ReportEmotionDataMissingException.EXCEPTION;
        }

        double positivePercentage = (double) positiveCount.get() / totalEmotions * 100;
        double negativePercentage = (double) negativeCount.get() / totalEmotions * 100;

        Optional<Asset> optionalAsset =
                (negativePercentage > positivePercentage) ? assetQueryRepository.findRandomAsset() : Optional.empty();
        Report report = buildReport(user, positivePercentage, negativePercentage, optionalAsset.orElse(null));
        emotionCounts.forEach(
                (emotion, count) -> report.addReportEmotion(buildReportEmotion(report, totalEmotions, emotion, count)));

        return reportRepository.save(report);
    }

    private ReportEmotion buildReportEmotion(Report report, int totalEmotions, Emotion emotion, Integer count) {
        int percentage = (int) ((double) count / totalEmotions * 100);
        return ReportEmotion.builder()
                .emotion(emotion)
                .report(report)
                .percentage(percentage)
                .build();
    }

    private Report buildReport(User user, double positivePercentage, double negativePercentage, Asset asset) {
        return Report.builder()
                .user(user)
                .positivePercentage(positivePercentage)
                .negativePercentage(negativePercentage)
                .asset(asset)
                .build();
    }
}
