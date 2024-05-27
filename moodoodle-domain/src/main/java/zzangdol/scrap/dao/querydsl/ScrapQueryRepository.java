package zzangdol.scrap.dao.querydsl;

import java.util.List;
import zzangdol.scrap.domain.Category;
import zzangdol.scrap.domain.Scrap;
import zzangdol.user.domain.User;

public interface ScrapQueryRepository {

    boolean existsByCategoryAndUserAndDiary(Category category, User user, Long diaryId);

    List<Scrap> findScrapsByUserAndCategory(User user, Long categoryId);

}
