package zzangdol.diary.implement;

import groovy.util.logging.Slf4j;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zzangdol.diary.dao.DiaryRepository;
import zzangdol.diary.domain.Diary;
import zzangdol.diary.domain.DiaryEmotion;
import zzangdol.diary.domain.Painting;
import zzangdol.diary.presentation.dto.request.DiaryCreateRequest;
import zzangdol.diary.presentation.dto.request.DiaryUpdateRequest;
import zzangdol.emotion.domain.Emotion;
import zzangdol.exception.custom.DiaryAccessDeniedException;
import zzangdol.exception.custom.DiaryDateOutOfBoundsException;
import zzangdol.exception.custom.DiaryDuplicateDateException;
import zzangdol.exception.custom.DiaryNotFoundException;
import zzangdol.user.domain.User;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class DiaryCommandServiceImpl implements DiaryCommandService {

    private final DiaryRepository diaryRepository;

    @Override
    public Diary createDiary(User user, DiaryCreateRequest request, String color,
                             List<Emotion> emotions) {
        validateDiaryDate(request.getDate());
        checkDiaryDuplication(user, request.getDate());
        Painting painting = buildPainting(request, color);
        Diary diary = buildDiary(user, request, emotions, painting);
        return diaryRepository.save(diary);
    }

    private void validateDiaryDate(LocalDate date) {
        if (date.isAfter(LocalDate.now())) {
            throw DiaryDateOutOfBoundsException.EXCEPTION;
        }
    }

    private void checkDiaryDuplication(User user, LocalDate date) {
        if (diaryRepository.existsByDateAndUserId(date, user.getId())) {
            throw DiaryDuplicateDateException.EXCEPTION;
        }
    }

    private Diary buildDiary(User user, DiaryCreateRequest request, List<Emotion> emotions, Painting painting) {
        Diary diary = Diary.builder()
                .date(request.getDate())
                .content(request.getContent())
                .user(user)
                .painting(painting)
                .build();
        emotions.forEach(emotion -> addEmotionToDiary(diary, emotion));
        return diary;
    }

    private void addEmotionToDiary(Diary diary, Emotion emotion) {
        DiaryEmotion diaryEmotion = DiaryEmotion.builder()
                .diary(diary)
                .emotion(emotion)
                .build();
        diaryEmotion.addDiaryEmotion(diary);
    }

    private Painting buildPainting(DiaryCreateRequest request, String color) {
        Painting painting = Painting.builder()
                .imageUrl(request.getImageUrl())
                .color(color)
                .build();
        return painting;
    }

    @Override
    public Diary updateDiary(User user, Long diaryId, DiaryUpdateRequest request) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> DiaryNotFoundException.EXCEPTION);
        checkDiaryOwnership(user, diary);
        if (request.getDate() != null) {
            validateDiaryDate(request.getDate());
            checkDiaryDuplication(user, request.getDate());
        }
        diary.updateDate(request.getDate());
        diary.updateContent(request.getContent());
        return diary;
    }

    @Override
    public void deleteDiary(User user, Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> DiaryNotFoundException.EXCEPTION);
        checkDiaryOwnership(user, diary);
        diaryRepository.deleteById(diaryId);
    }

    private void checkDiaryOwnership(User user, Diary diary) {
        if (!diary.getUser().getId().equals(user.getId())) {
            throw DiaryAccessDeniedException.EXCEPTION;
        }
    }

}
