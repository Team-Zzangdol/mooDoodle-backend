package zzangdol.scrap.business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import zzangdol.scrap.implement.CategoryCommandService;
import zzangdol.scrap.presentation.dto.request.CategoryCreateRequest;
import zzangdol.user.domain.User;

@RequiredArgsConstructor
@Component
public class CategoryFacade {

    private final CategoryCommandService categoryCommandService;

    public Long createCategory(User user, CategoryCreateRequest request) {
        return categoryCommandService.createCategory(user, request).getId();
    }

}
