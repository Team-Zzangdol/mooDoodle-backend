package zzangdol.diary.dao.querydsl;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import zzangdol.config.TestQueryDslConfig;
import zzangdol.diary.dao.DiaryRepository;
import zzangdol.diary.domain.Diary;
import zzangdol.user.dao.UserRepository;
import zzangdol.user.domain.User;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestQueryDslConfig.class)
@DataJpaTest
class DiaryQueryRepositoryTest {

    @Autowired
    private DiaryQueryRepository diaryQueryRepository;

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

    @DisplayName("특정 사용자의 특정 연도와 월에 대한 일기 목록을 조회한다.")
    @Test
    void findDiariesByUserAndYearAndMonth() {
        // given
        Diary diary1 = buildDiary(LocalDateTime.of(2024, 4, 1, 0, 0));
        Diary diary2 = buildDiary(LocalDateTime.of(2024, 4, 30, 23, 59));
        Diary diary3 = buildDiary(LocalDateTime.of(2024, 5, 1, 0, 0));
        Diary diary4 = buildDiary(LocalDateTime.of(2024, 3, 31, 23, 59));
        diaryRepository.saveAll(List.of(diary1, diary2, diary3, diary4));

        Long userId = user.getId();
        int year = 2024;
        int month = 4;

        // when
        List<Diary> result = diaryQueryRepository.findDiariesByUserAndYearAndMonth(
                userId,
                year,
                month);

        // then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
    }

    private Diary buildDiary(LocalDateTime date) {
        return Diary.builder()
                .user(user)
                .content("content")
                .date(date)
                .build();
    }

}