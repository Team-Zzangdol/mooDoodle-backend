package zzangdol.scrap.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zzangdol.diary.dao.DiaryRepository;
import zzangdol.diary.domain.Diary;
import zzangdol.exception.custom.DiaryNotFoundException;
import zzangdol.exception.custom.ScrapNotFoundException;
import zzangdol.scrap.dao.ScrapRepository;
import zzangdol.scrap.domain.Scrap;
import zzangdol.user.domain.User;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ScrapQueryServiceImpl implements ScrapQueryService {

    private final ScrapRepository scrapRepository;
    private final DiaryRepository diaryRepository;

    @Override
    public Scrap getScrapByUserAndDiary(User user, Long diaryId) {
        return scrapRepository.findScrapByUserAndDiaryId(user, diaryId)
                .orElseThrow(() -> ScrapNotFoundException.EXCEPTION);
    }

    @Override
    public boolean isDiaryScrappedByUser(User user, Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> DiaryNotFoundException.EXCEPTION);
        return scrapRepository.existsByUserAndDiary(user, diary);
    }
}
