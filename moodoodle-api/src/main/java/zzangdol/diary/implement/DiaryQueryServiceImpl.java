package zzangdol.diary.implement;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zzangdol.diary.dao.DiaryRepository;
import zzangdol.diary.domain.Diary;
import zzangdol.exception.custom.DiaryAccessDeniedException;
import zzangdol.exception.custom.DiaryNotFoundException;
import zzangdol.user.domain.User;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class DiaryQueryServiceImpl implements DiaryQueryService {

    private final DiaryRepository diaryRepository;

    @Override
    public Diary getDiaryByUser(User user, Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> DiaryNotFoundException.EXCEPTION);
        checkDiaryOwnership(user, diary);
        return diary;
    }

    @Override
    public List<Diary> getMonthlyDiariesByUser(User user, int year, int month) {
        return diaryRepository.findDiariesByUserAndYearAndMonth(user.getId(), year, month);
    }

    private void checkDiaryOwnership(User user, Diary diary) {
        if (!diary.getUser().getId().equals(user.getId())) {
            throw DiaryAccessDeniedException.EXCEPTION;
        }
    }

}
