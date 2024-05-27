package zzangdol.scrap.implement;

import zzangdol.scrap.domain.Scrap;
import zzangdol.user.domain.User;

public interface ScrapQueryService {

    Scrap getScrapByUserAndDiary(User user, Long diaryId);

    boolean isDiaryScrappedByUser(User user, Long diaryId);

}
