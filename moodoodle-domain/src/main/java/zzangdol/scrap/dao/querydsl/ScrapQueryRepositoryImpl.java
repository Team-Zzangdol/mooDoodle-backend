package zzangdol.scrap.dao.querydsl;

import static zzangdol.scrap.domain.QScrap.scrap;
import static zzangdol.scrap.domain.QScrapCategory.scrapCategory;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import zzangdol.scrap.domain.Category;
import zzangdol.user.domain.User;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ScrapQueryRepositoryImpl implements ScrapQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public boolean existsByCategoryAndUserAndDiary(Category category, User user, Long diaryId) {
        Integer count = queryFactory
                .selectOne()
                .from(scrap)
                .join(scrap.scrapCategories, scrapCategory)
                .where(scrapCategory.category.eq(category)
                        .and(scrap.user.eq(user))
                        .and(scrap.diary.id.eq(diaryId)))
                .fetchFirst();
        return count != null;
    }

}
