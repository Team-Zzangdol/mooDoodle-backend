package zzangdol.scrap.business;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import zzangdol.scrap.domain.Category;
import zzangdol.scrap.presentation.dto.response.CategoryListResponse;
import zzangdol.scrap.presentation.dto.response.CategoryResponse;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryMapper {

    public static CategoryResponse toCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public static CategoryListResponse toCategoryListResponse(List<Category> categories) {
        List<CategoryResponse> categoryResponses = categories.stream()
                .map(CategoryMapper::toCategoryResponse)
                .collect(Collectors.toList());

        return CategoryListResponse.builder()
                .categories(categoryResponses)
                .build();
    }
}
