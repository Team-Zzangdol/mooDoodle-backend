package zzangdol.scrap.dao.querydsl;

import zzangdol.scrap.domain.Category;
import zzangdol.user.domain.User;

public interface ScrapQueryRepository {

    boolean existsByCategoryAndUserAndDiary(Category category, User user, Long diaryId);

}
