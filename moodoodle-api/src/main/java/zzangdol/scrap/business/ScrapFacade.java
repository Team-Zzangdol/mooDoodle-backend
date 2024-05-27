package zzangdol.scrap.business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import zzangdol.scrap.implement.ScrapCommandService;
import zzangdol.scrap.implement.ScrapQueryService;
import zzangdol.user.domain.User;

@RequiredArgsConstructor
@Component
public class ScrapFacade {

    private final ScrapCommandService scrapCommandService;
    private final ScrapQueryService scrapQueryService;

    public Long createScrap(User user, Long diaryId) {
        return scrapCommandService.createScrap(user, diaryId).getId();
    }

    public void addCategoryToScrap(User user, Long scrapId, Long categoryId) {
        scrapCommandService.addCategoryToScrap(user, scrapId, categoryId);
    }

    public Boolean deleteScrap(User user, Long scrapId) {
        scrapCommandService.deleteScrap(user, scrapId);
        return true;
    }

    public Long getScrapByUserAndDiary(User user, Long diaryId) {
        return scrapQueryService.getScrapByUserAndDiary(user, diaryId).getId();
    }

}
