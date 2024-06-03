package zzangdol.scrap.dao.querydsl;

import static zzangdol.diary.domain.QDiary.diary;
import static zzangdol.scrap.domain.QCategory.category;
import static zzangdol.scrap.domain.QScrap.scrap;
import static zzangdol.scrap.domain.QScrapCategory.scrapCategory;

import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import zzangdol.constant.Constants;
import zzangdol.scrap.domain.Category;
import zzangdol.user.domain.User;

@Repository
@RequiredArgsConstructor
public class CategoryQueryRepositoryImpl implements CategoryQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Category> findCategoriesByUserOrderByLatestScrapCategory(User user) {
        return queryFactory.selectFrom(category)
                .leftJoin(category.scrapCategories, scrapCategory)
                .leftJoin(scrapCategory.scrap, scrap)
                .where(category.user.eq(user))
                .groupBy(category.id)
                .orderBy(
                        new CaseBuilder()
                                .when(category.name.eq(Constants.DEFAULT_CATEGORY_NAME)).then(0)
                                .otherwise(1).asc(),
                        scrapCategory.createdAt.max().desc().nullsLast()
                )
                .fetch();
    }

    @Override
    public String findLatestDiaryImageUrlByCategoryId(Long categoryId) {
        return queryFactory.select(diary.painting.imageUrl)
                .from(category)
                .join(category.scrapCategories, scrapCategory)
                .join(scrapCategory.scrap, scrap)
                .join(scrap.diary, diary)
                .where(category.id.eq(categoryId))
                .orderBy(diary.createdAt.desc())
                .fetchFirst();
    }
}
