package zzangdol.diary.implement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zzangdol.diary.dao.DiaryRepository;
import zzangdol.diary.domain.Diary;
import zzangdol.exception.custom.DiaryAccessDeniedException;
import zzangdol.user.dao.UserRepository;
import zzangdol.user.domain.User;

@SpringBootTest
class DiaryQueryServiceTest {

    @Autowired
    private DiaryQueryService diaryQueryService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DiaryRepository diaryRepository;

    private User user;

    @BeforeEach
    void setUp() {
        diaryRepository.deleteAll();
        userRepository.deleteAll();
        user = User.builder().build();
        userRepository.save(user);
    }

    @AfterEach
    void tearDown() {
        diaryRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @DisplayName("특정 일기를 조회한다.")
    @Test
    void getDiaryByUser() {
        // given
        Diary diary = buildDiary(LocalDateTime.of(2024, 4, 1, 0, 0));
        diary = diaryRepository.save(diary);

        // when
        Diary result = diaryQueryService.getDiaryByUser(user, diary.getId());

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(diary.getId());
        assertThat(result.getContent()).isEqualTo("content");
        assertThat(result.getDate()).isEqualTo(LocalDateTime.of(2024, 4, 1, 0, 0));
    }

    @DisplayName("특정 사용자의 특정 연도와 월에 대한 일기 목록을 조회한다.")
    @Test
    void getMonthlyDiariesByUser() {
        // given
        Diary diary1 = buildDiary(LocalDateTime.of(2024, 4, 1, 0, 0));
        Diary diary2 = buildDiary(LocalDateTime.of(2024, 4, 30, 23, 59));
        Diary diary3 = buildDiary(LocalDateTime.of(2024, 5, 1, 0, 0));
        Diary diary4 = buildDiary(LocalDateTime.of(2024, 3, 31, 23, 59));
        diaryRepository.saveAll(List.of(diary1, diary2, diary3, diary4));

        int year = 2024;
        int month = 4;

        // when
        List<Diary> result = diaryQueryService.getMonthlyDiariesByUser(user, year, month);

        // then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).extracting("date")
                .containsExactlyInAnyOrder(
                        LocalDateTime.of(2024, 4, 1, 0, 0),
                        LocalDateTime.of(2024, 4, 30, 23, 59));
    }

    @DisplayName("다른 사용자의 일기를 조회하려 할 때 접근 거부 예외를 발생시킨다.")
    @Test
    void accessDeniedWhenUserIsNotOwner() {
        // given
        Diary diary = buildDiary(LocalDateTime.of(2024, 4, 1, 0, 0));
        diaryRepository.save(diary);
        User otherUser = User.builder().build();

        // when & then
        assertThrows(DiaryAccessDeniedException.class, () -> {
            diaryQueryService.getDiaryByUser(otherUser, diary.getId());
        });
    }

    private Diary buildDiary(LocalDateTime date) {
        return Diary.builder()
                .user(user)
                .content("content")
                .date(date)
                .build();
    }
}