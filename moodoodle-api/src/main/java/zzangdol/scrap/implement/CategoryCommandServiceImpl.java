package zzangdol.scrap.implement;

import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zzangdol.constant.Constants;
import zzangdol.exception.custom.CategoryAccessDeniedException;
import zzangdol.exception.custom.CategoryNotFoundException;
import zzangdol.exception.custom.DefaultCategoryAccessDeniedException;
import zzangdol.scrap.dao.CategoryRepository;
import zzangdol.scrap.domain.Category;
import zzangdol.scrap.presentation.dto.request.CategoryCreateRequest;
import zzangdol.user.domain.User;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class CategoryCommandServiceImpl implements CategoryCommandService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category createCategory(User user, CategoryCreateRequest request) {
        Category category = buildCategory(request, user);
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(User user, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> CategoryNotFoundException.EXCEPTION);
        checkCategoryOwnership(user, category);
        checkDefaultCategory(category);
        categoryRepository.deleteById(categoryId);
    }

    private void checkDefaultCategory(Category category) {
        if (category.getName().equals(Constants.DEFAULT_CATEGORY_NAME)) {
            throw DefaultCategoryAccessDeniedException.EXCEPTION;
        }
    }

    private void checkCategoryOwnership(User user, Category category) {
        if (!category.getUser().getId().equals(user.getId())) {
            throw CategoryAccessDeniedException.EXCEPTION;
        }
    }

    private Category buildCategory(CategoryCreateRequest request, User user) {
        return Category.builder()
                .name(request.getName())
                .user(user)
                .build();
    }

}
