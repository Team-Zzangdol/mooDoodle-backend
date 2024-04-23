package zzangdol.diary.implement;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zzangdol.diary.dao.DiaryRepository;
import zzangdol.diary.dao.querydsl.DiaryQueryRepository;
import zzangdol.diary.domain.Diary;
import zzangdol.exception.custom.DiaryAccessDeniedException;
import zzangdol.moodoodlecommon.exception.custom.DiaryNotFoundException;
import zzangdol.user.domain.User;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class DiaryQueryServiceImpl implements DiaryQueryService {

    private final DiaryRepository diaryRepository;
    private final DiaryQueryRepository diaryQueryRepository;

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
        return diaryQueryRepository.findDiariesByUserAndMonth(user.getId(), year, month);
    }

}
