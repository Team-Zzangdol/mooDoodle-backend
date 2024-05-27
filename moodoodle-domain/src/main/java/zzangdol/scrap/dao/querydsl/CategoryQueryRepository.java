package zzangdol.scrap.dao.querydsl;

import java.util.List;
import zzangdol.scrap.domain.Category;
import zzangdol.user.domain.User;

public interface CategoryQueryRepository {

    List<Category> findCategoriesByUserOrderByLatestScrapCategory(User user);

    String findLatestDiaryImageUrlByCategoryId(Long categoryId);

}
