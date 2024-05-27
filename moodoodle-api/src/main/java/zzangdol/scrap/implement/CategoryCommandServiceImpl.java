package zzangdol.scrap.implement;

import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    private Category buildCategory(CategoryCreateRequest request, User user) {
        return Category.builder()
                .name(request.getName())
                .user(user)
                .build();
    }

}
