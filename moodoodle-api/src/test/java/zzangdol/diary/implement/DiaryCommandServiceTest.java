package zzangdol.diary.implement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zzangdol.diary.dao.DiaryEmotionRepository;
import zzangdol.diary.dao.DiaryRepository;
import zzangdol.diary.domain.Diary;
import zzangdol.diary.presentation.dto.request.DiaryCreateRequest;
import zzangdol.diary.presentation.dto.request.DiaryUpdateRequest;
import zzangdol.emotion.dao.EmotionRepository;
import zzangdol.emotion.domain.Emotion;
import zzangdol.exception.custom.DiaryAccessDeniedException;
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
        DiaryCreateRequest request = buildValidDiaryCreateRequest(LocalDateTime.of(2024, 1, 1, 0, 0));

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

    @DisplayName("일기 생성 시 중복된 날짜에 대한 예외를 발생시킨다")
    @Test
    void shouldNotCreateDiaryWithDuplicatedDate() {
        // given
        DiaryCreateRequest request = buildValidDiaryCreateRequest(LocalDateTime.of(2024, 1, 1, 0, 0));
        diaryCommandService.createDiary(user, request, "FFFFFF", emotions);

        // when & then
        assertThatThrownBy(() -> diaryCommandService.createDiary(user, request, "FFFFFF", emotions))
                .isInstanceOf(DiaryDuplicateDateException.class)
                .hasMessageContaining("해당 날짜에 이미 일기가 존재합니다.");
    }

    @DisplayName("기존 일기를 수정한다.")
    @Test
    void updateDiary() {
        // given
        DiaryCreateRequest createRequest = buildValidDiaryCreateRequest(LocalDateTime.of(2024, 1, 1, 0, 0));
        Diary createdDiary = diaryCommandService.createDiary(user, createRequest, "FFFFFF", emotions);

        DiaryUpdateRequest updateRequest = buildValidDiaryUpdateRequest(
                "updated content",
                LocalDateTime.of(2023, 1, 1, 0, 0)
        );

        // when
        Diary updatedDiary = diaryCommandService.updateDiary(user, createdDiary.getId(), updateRequest);

        // then
        assertThat(updatedDiary).isNotNull();
        assertThat(updatedDiary.getId()).isNotNull();
        assertThat(updatedDiary.getContent()).isEqualTo("updated content");
        assertThat(updatedDiary.getPainting().getColor()).isEqualTo("FFFFFF");
        assertThat(updatedDiary.getDate()).isEqualTo(LocalDateTime.of(2023, 1, 1, 0, 0));

        assertThat(createdDiary.getDiaryEmotions())
                .hasSize(2)
                .extracting("emotion")
                .containsExactlyElementsOf(emotions);
    }

    @DisplayName("기존 일기의 내용만 수정한다.")
    @Test
    void updateDiaryContentOnly() {
        // given
        DiaryCreateRequest createRequest = buildValidDiaryCreateRequest(LocalDateTime.of(2024, 1, 1, 0, 0));
        Diary createdDiary = diaryCommandService.createDiary(user, createRequest, "FFFFFF", emotions);

        DiaryUpdateRequest updateRequest = buildValidDiaryUpdateRequest("updated content", null);

        // when
        Diary updatedDiary = diaryCommandService.updateDiary(user, createdDiary.getId(), updateRequest);

        // then
        assertThat(updatedDiary).isNotNull();
        assertThat(updatedDiary.getId()).isNotNull();
        assertThat(updatedDiary.getContent()).isEqualTo("updated content");
        assertThat(updatedDiary.getPainting().getColor()).isEqualTo("FFFFFF");
        assertThat(updatedDiary.getDate()).isEqualTo(LocalDateTime.of(2024, 1, 1, 0, 0));

        assertThat(createdDiary.getDiaryEmotions())
                .hasSize(2)
                .extracting("emotion")
                .containsExactlyElementsOf(emotions);
    }

    @DisplayName("기존 일기의 날짜만 수정한다.")
    @Test
    void updateDiaryDateOnly() {
        // given
        DiaryCreateRequest createRequest = buildValidDiaryCreateRequest(LocalDateTime.of(2024, 1, 1, 0, 0));
        Diary createdDiary = diaryCommandService.createDiary(user, createRequest, "FFFFFF", emotions);

        DiaryUpdateRequest updateRequest = buildValidDiaryUpdateRequest(null, LocalDateTime.of(2023, 1, 1, 0, 0));

        // when
        Diary updatedDiary = diaryCommandService.updateDiary(user, createdDiary.getId(), updateRequest);

        // then
        assertThat(updatedDiary).isNotNull();
        assertThat(updatedDiary.getId()).isNotNull();
        assertThat(updatedDiary.getContent()).isEqualTo("content");
        assertThat(updatedDiary.getPainting().getColor()).isEqualTo("FFFFFF");
        assertThat(updatedDiary.getDate()).isEqualTo(LocalDateTime.of(2023, 1, 1, 0, 0));

        assertThat(createdDiary.getDiaryEmotions())
                .hasSize(2)
                .extracting("emotion")
                .containsExactlyElementsOf(emotions);
    }

    @DisplayName("일기는 오늘 날짜 이후로 수정할 수 없다.")
    @Test
    void shouldNotUpdateDiaryWithFutureDate() {
        // given
        DiaryCreateRequest createRequest = buildValidDiaryCreateRequest(LocalDateTime.of(2024, 1, 1, 0, 0));
        Diary createdDiary = diaryCommandService.createDiary(user, createRequest, "FFFFFF", emotions);

        DiaryUpdateRequest updateRequest = buildValidDiaryUpdateRequest(null, LocalDateTime.now().plusDays(1));

        // when & then
        assertThatThrownBy(() -> diaryCommandService.updateDiary(user, createdDiary.getId(), updateRequest))
                .isInstanceOf(DiaryDateOutOfBoundsException.class)
                .hasMessageContaining("일기는 오늘 날짜 이후로 생성할 수 없습니다.");
    }

    @DisplayName("일기 수정 시 중복된 날짜에 대한 예외를 발생시킨다")
    @Test
    void shouldNotUpdateDiaryWithDuplicatedDate() {
        // given
        DiaryCreateRequest createRequest1 = buildValidDiaryCreateRequest(LocalDateTime.of(2024, 1, 1, 0, 0));
        diaryCommandService.createDiary(user, createRequest1, "FFFFFF", emotions);

        DiaryCreateRequest createRequest2 = buildValidDiaryCreateRequest(LocalDateTime.of(2023, 1, 1, 0, 0));
        Diary createdDiary = diaryCommandService.createDiary(user, createRequest2, "FFFFFF", emotions);

        DiaryUpdateRequest updateRequest = buildValidDiaryUpdateRequest(null, LocalDateTime.of(2024, 1, 1, 0, 0));

        // when & then
        assertThatThrownBy(() -> diaryCommandService.updateDiary(user, createdDiary.getId(), updateRequest))
                .isInstanceOf(DiaryDuplicateDateException.class)
                .hasMessageContaining("해당 날짜에 이미 일기가 존재합니다.");
    }

    @Test
    @DisplayName("다른 사용자의 일기를 수정하려 할 때 접근 거부 예외를 발생시킨다")
    void shouldThrowAccessDeniedWhenUpdatingOtherUsersDiary() {
        // given
        User otherUser = User.builder().build();
        userRepository.save(otherUser);

        DiaryCreateRequest createRequest = buildValidDiaryCreateRequest(LocalDateTime.of(2024, 1, 1, 0, 0));
        Diary diary = diaryCommandService.createDiary(user, createRequest, "FFFFFF", emotions);

        DiaryUpdateRequest updateRequest = buildValidDiaryUpdateRequest("updated content", LocalDateTime.now());

        // when & then
        assertThrows(DiaryAccessDeniedException.class, () -> {
            diaryCommandService.updateDiary(otherUser, diary.getId(), updateRequest);
        });
    }


    @Test
    @DisplayName("일기를 성공적으로 삭제한다")
    void deleteDiarySuccessfully() {
        // given
        DiaryCreateRequest createRequest = buildValidDiaryCreateRequest(LocalDateTime.of(2024, 1, 1, 0, 0));
        Diary createdDiary = diaryCommandService.createDiary(user, createRequest, "FFFFFF", emotions);

        // when
        diaryCommandService.deleteDiary(user, createdDiary.getId());

        // then
        assertThatThrownBy(() -> diaryRepository.findById(createdDiary.getId()).orElseThrow())
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("No value present");
    }

    @Test
    @DisplayName("존재하지 않는 일기를 삭제하려 할 때 예외를 발생시킨다")
    void throwExceptionWhenDeleteNonExistentDiary() {
        // given
        Long nonExistentDiaryId = 999L;  // 존재하지 않는 ID

        // when & then
        assertThrows(zzangdol.moodoodlecommon.exception.custom.DiaryNotFoundException.class, () -> {
            diaryCommandService.deleteDiary(user, nonExistentDiaryId);
        });
    }

    @Test
    @DisplayName("다른 사용자의 일기를 삭제하려 할 때 접근 거부 예외를 발생시킨다")
    void shouldThrowAccessDeniedWhenDeletingOtherUsersDiary() {
        // given
        User otherUser = User.builder().build();
        userRepository.save(otherUser);

        DiaryCreateRequest createRequest = buildValidDiaryCreateRequest(LocalDateTime.of(2024, 1, 1, 0, 0));
        Diary diary = diaryCommandService.createDiary(user, createRequest, "FFFFFF", emotions);

        // when & then
        assertThrows(DiaryAccessDeniedException.class, () -> {
            diaryCommandService.deleteDiary(otherUser, diary.getId());
        });
    }

    private DiaryCreateRequest buildValidDiaryCreateRequest(LocalDateTime date) {
        return DiaryCreateRequest.builder()
                .content("content")
                .date(date)
                .imageUrl("imageUrl")
                .build();
    }

    private DiaryUpdateRequest buildValidDiaryUpdateRequest(String content, LocalDateTime date) {
        return DiaryUpdateRequest.builder()
                .content(content)
                .date(date)
                .build();
    }

}