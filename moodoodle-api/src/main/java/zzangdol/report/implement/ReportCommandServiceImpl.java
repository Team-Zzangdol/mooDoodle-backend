package zzangdol.report.implement;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zzangdol.diary.dao.DiaryRepository;
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
    private final DiaryRepository diaryRepository;
    private final AssetQueryRepository assetQueryRepository;

    @Scheduled(cron = "0 0 0 * * MON")
    @Override
    public Report createReport(User user) {
        LocalDate today = LocalDate.now();
        LocalDate lastMonday = today.with(java.time.DayOfWeek.MONDAY).minusWeeks(1);
        LocalDate thisSunday = lastMonday.plusDays(6);

        List<Diary> diaries = diaryRepository.findDiariesBetweenByUser(user.getId(), lastMonday, thisSunday);
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
                if (emotion.getPolarity().equals(EmotionPolarity.POSITIVE)) {
                    positiveCount.getAndIncrement();
                } else {
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
                (emotion, count) -> report.addReportEmotion(buildReportEmotion(report, totalEmotions, emotion)));

        user.setRead(false);

        return reportRepository.save(report);
    }

    @Override
    public Report createReportByDate(User user, LocalDate startDate, LocalDate endDate) {
        List<Diary> diaries = diaryRepository.findDiariesBetweenByUser(user.getId(), startDate, endDate);
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
                if (emotion.getPolarity().equals(EmotionPolarity.POSITIVE)) {
                    positiveCount.getAndIncrement();
                } else {
                    negativeCount.getAndIncrement();
                }
            });
        }

        int totalEmotions = positiveCount.get() + negativeCount.get();
        if (totalEmotions == 0) {
            throw ReportEmotionDataMissingException.EXCEPTION;
        }

        int positivePercentage = (int) Math.round((double) positiveCount.get() / totalEmotions * 100);
        int negativePercentage = (int) Math.round((double) negativeCount.get() / totalEmotions * 100);

        Optional<Asset> optionalAsset =
                (negativePercentage > positivePercentage) ? assetQueryRepository.findRandomAsset() : Optional.empty();
        Report report = buildReport(user, positivePercentage, negativePercentage, optionalAsset.orElse(null));

        emotionCounts.entrySet().stream()
                .sorted(Map.Entry.<Emotion, Integer>comparingByValue().reversed())
                .limit(3)
                .forEach(entry -> {
                    Emotion emotion = entry.getKey();
                    Integer count = entry.getValue();
                    int percentage = (int) Math.round((double) count / totalEmotions * 100);
                    report.addReportEmotion(buildReportEmotion(report, percentage, emotion));
                });

        user.setRead(false);

        return reportRepository.save(report);
    }

    private ReportEmotion buildReportEmotion(Report report, int percentage, Emotion emotion) {
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
