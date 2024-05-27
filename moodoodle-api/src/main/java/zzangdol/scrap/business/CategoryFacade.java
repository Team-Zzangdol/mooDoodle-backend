package zzangdol.scrap.business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import zzangdol.scrap.implement.CategoryCommandService;
import zzangdol.scrap.implement.CategoryQueryService;
import zzangdol.scrap.presentation.dto.request.CategoryCreateRequest;
import zzangdol.scrap.presentation.dto.response.CategoryListResponse;
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
        return CategoryMapper.toCategoryListResponse(categoryQueryService.getCategoriesByUser(user));
    }

}
