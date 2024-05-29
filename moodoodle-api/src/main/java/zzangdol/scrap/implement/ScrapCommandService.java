package zzangdol.scrap.implement;

import zzangdol.user.domain.User;

public interface ScrapCommandService {

    void handleScrap(User user, Long diaryId);

    void handleCategoryToScrap(User user, Long scrapId, Long categoryId);

    void deleteScrap(User user, Long scrapId);

}
