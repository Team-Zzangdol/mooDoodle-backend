package zzangdol.scrap.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zzangdol.exception.custom.ScrapNotFoundException;
import zzangdol.scrap.dao.ScrapRepository;
import zzangdol.scrap.domain.Scrap;
import zzangdol.user.domain.User;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ScrapQueryServiceImpl implements ScrapQueryService {

    private final ScrapRepository scrapRepository;

    @Override
    public Scrap getScrapByUserAndDiary(User user, Long diaryId) {
        return scrapRepository.findScrapByUserAndDiaryId(user, diaryId)
                .orElseThrow(() -> ScrapNotFoundException.EXCEPTION);
    }
}
