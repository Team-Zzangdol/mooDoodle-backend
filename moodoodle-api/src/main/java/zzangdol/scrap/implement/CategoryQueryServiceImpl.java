package zzangdol.scrap.implement;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zzangdol.constant.Constants;
import zzangdol.diary.domain.Diary;
import zzangdol.scrap.business.CategoryMapper;
import zzangdol.scrap.dao.CategoryRepository;
import zzangdol.scrap.dao.ScrapRepository;
import zzangdol.scrap.domain.Category;
import zzangdol.scrap.domain.Scrap;
import zzangdol.scrap.presentation.dto.response.CategoryResponse;
import zzangdol.scrap.presentation.dto.response.ScrapCategoryResponse;
import zzangdol.user.domain.User;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CategoryQueryServiceImpl implements CategoryQueryService {

    private final CategoryRepository categoryRepository;
    private final ScrapRepository scrapRepository;

    @Override
    public List<Category> getCategoriesByUser(User user) {
        return categoryRepository.findCategoriesByUserOrderByLatestScrapCategory(user);
    }

    @Override
    public List<CategoryResponse> getCategoryResponsesByUser(User user) {
        List<Category> categories = getCategoriesByUser(user);
        return categories.stream()
                .map(category -> {
                    String latestImageUrl = categoryRepository.findLatestDiaryImageUrlByCategoryId(category.getId());
                    return CategoryMapper.toCategoryResponse(category, latestImageUrl);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ScrapCategoryResponse> getScrapCategoryResponsesByUser(User user, Long diaryId) {
        List<Category> categories = getCategoriesByUser(user);
        return categories.stream()
                .filter(category -> !category.getName().equals(Constants.DEFAULT_CATEGORY_NAME))
                .map(category -> {
                    boolean isScrapped = scrapRepository.existsByCategoryAndUserAndDiary(category, user, diaryId);
                    String latestImageUrl = categoryRepository.findLatestDiaryImageUrlByCategoryId(category.getId());
                    return CategoryMapper.toScrapCategoryResponse(category, isScrapped, latestImageUrl);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Diary> getDiariesByCategory(User user, Long categoryId) {
        List<Scrap> scraps = scrapRepository.findScrapsByUserAndCategory(user, categoryId);
        return scraps.stream()
                .map(Scrap::getDiary)
                .collect(Collectors.toList());
    }

}
