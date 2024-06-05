package zzangdol.scrap.business;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import zzangdol.diary.business.DiaryMapper;
import zzangdol.diary.domain.Diary;
import zzangdol.diary.presentation.dto.response.CategoryDiaryListResponse;
import zzangdol.scrap.implement.CategoryCommandService;
import zzangdol.scrap.implement.CategoryQueryService;
import zzangdol.scrap.presentation.dto.request.CategoryCreateRequest;
import zzangdol.scrap.presentation.dto.response.CategoryListResponse;
import zzangdol.scrap.presentation.dto.response.CategoryResponse;
import zzangdol.scrap.presentation.dto.response.ScrapCategoryListResponse;
import zzangdol.scrap.presentation.dto.response.ScrapCategoryResponse;
import zzangdol.user.domain.User;

@RequiredArgsConstructor
@Component
public class CategoryFacade {

    private final CategoryCommandService categoryCommandService;
    private final CategoryQueryService categoryQueryService;

    public Long createCategory(User user, CategoryCreateRequest request) {
        return categoryCommandService.createCategory(user, request).getId();
    }

    public CategoryListResponse getCategoriesByUser(User user) {
        List<CategoryResponse> categoryResponses = categoryQueryService.getCategoryResponsesByUser(user);
        return CategoryMapper.toCategoryListResponse(categoryResponses);
    }

    public ScrapCategoryListResponse getScrapCategoriesByUser(User user, Long diaryId) {
        List<ScrapCategoryResponse> categoryResponses = categoryQueryService.getScrapCategoryResponsesByUser(user, diaryId);
        return CategoryMapper.toScrapCategoryListResponse(categoryResponses);
    }

    public CategoryDiaryListResponse getDiariesByCategory(User user, Long categoryId) {
        List<Diary> diariesByCategory = categoryQueryService.getDiariesByCategory(user, categoryId);
        return DiaryMapper.toCategoryDiaryListResponse(diariesByCategory);
    }

    public void deleteCategory(User user, Long categoryId) {
        categoryCommandService.deleteCategory(user, categoryId);
    }
}