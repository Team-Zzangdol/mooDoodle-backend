package zzangdol.moodoodleapi.diary.implement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalDateTime;
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
import zzangdol.emotion.dao.EmotionRepository;
import zzangdol.emotion.domain.Emotion;
import zzangdol.moodoodleapi.diary.presentation.dto.request.DiaryCreateRequest;
import zzangdol.moodoodlecommon.exception.custom.DiaryDateOutOfBoundsException;
import zzangdol.moodoodlecommon.exception.custom.DiaryDuplicateDateException;
import zzangdol.user.dao.UserRepository;
import zzangdol.user.domain.User;

@SpringBootTest
class DiaryCommandServiceTest {

    @Autowired
    private DiaryCommandService diaryCommandService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DiaryRepository diaryRepository;

    @Autowired
    private EmotionRepository emotionRepository;

    @Autowired
    private DiaryEmotionRepository diaryEmotionRepository;

    private User user;
    private Emotion emotion1;
    private Emotion emotion2;
    private List<Emotion> emotions;


    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        diaryEmotionRepository.deleteAllInBatch();
        emotionRepository.deleteAll();
        diaryRepository.deleteAll();
        user = User.builder().build();
        userRepository.save(user);
        emotion1 = Emotion.builder().name("happy").build();
        emotion2 = Emotion.builder().name("sad").build();
        emotionRepository.saveAll(List.of(emotion1, emotion2));
        emotions = List.of(emotion1, emotion2);
    }

    @AfterEach
    void tearDown() {
        diaryEmotionRepository.deleteAllInBatch();
        emotionRepository.deleteAllInBatch();
        diaryRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @DisplayName("새로운 일기를 등록한다.")
    @Test
    void createDiary() {
        // given
        DiaryCreateRequest request = buildValidDiaryCreateRequest();

        // when
        Diary createdDiary = diaryCommandService.createDiary(user, request, "FFFFFF", emotions);

        // then
        assertThat(createdDiary).isNotNull();
        assertThat(createdDiary.getId()).isNotNull();
        assertThat(createdDiary.getContent()).isEqualTo("content");
        assertThat(createdDiary.getPainting().getColor()).isEqualTo("FFFFFF");
        assertThat(createdDiary.getDate()).isEqualTo(LocalDateTime.of(2024, 1, 1, 0, 0));

        assertThat(createdDiary.getDiaryEmotions())
                .hasSize(2)
                .extracting("emotion")
                .containsExactlyElementsOf(emotions);
    }

    @DisplayName("일기는 오늘 날짜 이후로 생성할 수 없다.")
    @Test
    void shouldNotCreateDiaryWithFutureDate() {
        // given
        LocalDateTime futureDate = LocalDateTime.now().plusDays(1);
        DiaryCreateRequest request = DiaryCreateRequest.builder()
                .date(futureDate)
                .content("content")
                .imageUrl("imageUrl")
                .build();

        // when & then
        assertThatThrownBy(() -> diaryCommandService.createDiary(user, request, "FFFFFF", emotions))
                .isInstanceOf(DiaryDateOutOfBoundsException.class)
                .hasMessageContaining("일기는 오늘 날짜 이후로 생성할 수 없습니다.");
    }

    @DisplayName("한 날짜에는 하나의 일기만 작성할 수 있다.")
    @Test
    void shouldNotCreateDiaryWithDuplicatedDate() {
        // given
        DiaryCreateRequest request = buildValidDiaryCreateRequest();
        diaryCommandService.createDiary(user, request, "FFFFFF", emotions);

        // when & then
        assertThatThrownBy(() -> diaryCommandService.createDiary(user, request, "FFFFFF", emotions))
                .isInstanceOf(DiaryDuplicateDateException.class)
                .hasMessageContaining("해당 날짜에 이미 일기가 존재합니다.");
    }

    private DiaryCreateRequest buildValidDiaryCreateRequest() {
        DiaryCreateRequest request = DiaryCreateRequest.builder()
                .content("content")
                .date(LocalDateTime.of(2024, 1, 1, 0, 0))
                .imageUrl("imageUrl")
                .build();
        return request;
    }

}