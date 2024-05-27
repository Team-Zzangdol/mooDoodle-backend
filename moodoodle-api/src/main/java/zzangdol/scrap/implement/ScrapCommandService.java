package zzangdol.scrap.implement;

import zzangdol.scrap.domain.Scrap;
import zzangdol.user.domain.User;

public interface ScrapCommandService {

    Scrap createScrap(User user, Long diaryId);

    void addCategoryToScrap(User user, Long scrapId, Long categoryId);

}
