package zzangdol.report.implement;

import static org.assertj.core.api.Assertions.assertThat;
import static zzangdol.emotion.domain.EmotionPolarity.NEGATIVE;
import static zzangdol.emotion.domain.EmotionPolarity.POSITIVE;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zzangdol.diary.dao.DiaryEmotionRepository;
import zzangdol.diary.dao.DiaryRepository;
import zzangdol.diary.domain.Diary;
import zzangdol.diary.domain.DiaryEmotion;
import zzangdol.emotion.dao.EmotionRepository;
import zzangdol.emotion.domain.Emotion;
import zzangdol.report.dao.AssetRepository;
import zzangdol.report.dao.ReportEmotionRepository;
import zzangdol.report.dao.ReportRepository;
import zzangdol.report.domain.Asset;
import zzangdol.report.domain.Report;
import zzangdol.user.dao.UserRepository;
import zzangdol.user.domain.User;

@SpringBootTest
class ReportCommandServiceTest {

    @Autowired
    private ReportCommandService reportCommandService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DiaryRepository diaryRepository;

    @Autowired
    private EmotionRepository emotionRepository;

    @Autowired
    private DiaryEmotionRepository diaryEmotionRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ReportEmotionRepository reportEmotionRepository;

    @Autowired
    private AssetRepository assetRepository;

    private User user;
    private Emotion emotion1;
    private Emotion emotion2;
    private Emotion emotion3;

    @BeforeEach
    void setUp() {
        reportEmotionRepository.deleteAll();
        reportRepository.deleteAll();
        userRepository.deleteAll();
        diaryEmotionRepository.deleteAll();
        emotionRepository.deleteAll();
        diaryRepository.deleteAll();
        user = User.builder().build();
        userRepository.save(user);
        emotion1 = Emotion.builder()
                .name("happy")
                .polarity(POSITIVE)
                .build();
        emotion2 = Emotion.builder()
                .name("sad")
                .polarity(NEGATIVE)
                .build();
        emotion3 = Emotion.builder()
                .name("angry")
                .polarity(NEGATIVE)
                .build();
        emotionRepository.saveAll(List.of(emotion1, emotion2, emotion3));
        assetRepository.save(Asset.builder().build());
    }

    @AfterEach
    void tearDown() {
        reportEmotionRepository.deleteAllInBatch();
        reportRepository.deleteAllInBatch();
        diaryEmotionRepository.deleteAllInBatch();
        emotionRepository.deleteAllInBatch();
        diaryRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @DisplayName("새로운 리포트를 생성한다.")
    @Test
    void createReport() {
        // given
        Diary diary1 = buildDiary(LocalDate.of(2024, 4, 28));   // Week 5 of April
        Diary diary2 = buildDiary(LocalDate.of(2024, 4, 29));   // Week 1 of May
        Diary diary3 = buildDiary(LocalDate.of(2024, 5, 1));   // Week 1 of May
        Diary diary4 = buildDiary(LocalDate.of(2024, 5, 4));   // Week 1 of May
        Diary diary5 = buildDiary(LocalDate.of(2024, 5, 5));   // Week 1 of May
        Diary diary6 = buildDiary(LocalDate.of(2024, 5, 6));   // Week 2 of May
        diaryRepository.saveAll(List.of(diary1, diary2, diary3, diary4, diary5, diary6));

        setDiaryEmotion(diary1, List.of(emotion1, emotion2));
        setDiaryEmotion(diary2, List.of(emotion1, emotion2, emotion3));
        setDiaryEmotion(diary3, List.of(emotion3, emotion1));
        setDiaryEmotion(diary4, List.of(emotion1, emotion2));
        setDiaryEmotion(diary5, List.of(emotion1, emotion2, emotion3));
        setDiaryEmotion(diary6, List.of(emotion3, emotion1));

        int year = 2024;
        int month = 5;
        int week = 1;

        // when
        Report report = reportCommandService.createReport(user, year, month, week);

        // then
        assertThat(report.getPositivePercentage()).isEqualTo(40.0);
        assertThat(report.getNegativePercentage()).isEqualTo(60.0);
        assertThat(report.getReportEmotions().size()).isEqualTo(3);
        assertThat(report.getAsset()).isNotNull();
    }

    private Diary buildDiary(LocalDate date) {
        return Diary.builder()
                .user(user)
                .content("content")
                .date(date)
                .build();
    }

    private void setDiaryEmotion(Diary diary, List<Emotion> emotions) {
        for (Emotion emotion : emotions) {
            DiaryEmotion diaryEmotion = DiaryEmotion.builder()
                    .diary(diary)
                    .emotion(emotion)
                    .build();
            diaryEmotion.addDiaryEmotion(diary);
            diaryEmotionRepository.save(diaryEmotion);
        }
    }

}