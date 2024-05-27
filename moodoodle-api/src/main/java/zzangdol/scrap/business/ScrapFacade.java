package zzangdol.scrap.business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import zzangdol.scrap.implement.ScrapCommandService;
import zzangdol.user.domain.User;

@RequiredArgsConstructor
@Component
public class ScrapFacade {

    private final ScrapCommandService scrapCommandService;

    public Long createScrap(User user, Long diaryId) {
        return scrapCommandService.createScrap(user, diaryId).getId();
    }
}
