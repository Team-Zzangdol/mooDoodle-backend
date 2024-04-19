package zzangdol.moodoodleapi.diary.implement;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zzangdol.diary.dao.DiaryRepository;
import zzangdol.diary.domain.Diary;
import zzangdol.moodoodlecommon.exception.custom.DiaryAccessDeniedException;
import zzangdol.moodoodlecommon.exception.custom.DiaryNotFoundException;
import zzangdol.user.domain.User;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class DiaryQueryServiceImpl implements DiaryQueryService {

    private final DiaryRepository diaryRepository;

    @Override
    public Diary getDiaryById(User user, Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> DiaryNotFoundException.EXCEPTION);
        if (!diary.getUser().equals(user)) {
            throw DiaryAccessDeniedException.EXCEPTION;
        }
        return diary;
    }

    @Override
    public List<Diary> getMonthlyDiariesByUser(User user, int year, int month) {
        return null;
    }

}
