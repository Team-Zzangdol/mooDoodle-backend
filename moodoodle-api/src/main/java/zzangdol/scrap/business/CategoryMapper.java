package zzangdol.scrap.business;

import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import zzangdol.constant.Constants;
import zzangdol.scrap.domain.Category;
import zzangdol.scrap.presentation.dto.response.CategoryListResponse;
import zzangdol.scrap.presentation.dto.response.CategoryResponse;
import zzangdol.scrap.presentation.dto.response.ScrapCategoryListResponse;
import zzangdol.scrap.presentation.dto.response.ScrapCategoryResponse;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryMapper {

    public static CategoryResponse toCategoryResponse(Category category, String latestImageUrl) {
        return CategoryResponse.builder()
                .categoryId(category.getId())
                .name(category.getName())
                .imageUrl(latestImageUrl != null ? latestImageUrl : Constants.DEFAULT_IMAGE_URL)
                .scrapCount(category.getScrapCategories().size())
                .build();
    }

    public static ScrapCategoryResponse toScrapCategoryResponse(Category category, boolean isScraped, String latestImageUrl) {
        return ScrapCategoryResponse.builder()
                .categoryId(category.getId())
                .name(category.getName())
                .imageUrl(latestImageUrl != null ? latestImageUrl : Constants.DEFAULT_IMAGE_URL)
                .scrapCount(category.getScrapCategories().size())
                .isScraped(isScraped)
                .build();
    }

    public static CategoryListResponse toCategoryListResponse(List<CategoryResponse> categories) {
        return CategoryListResponse.builder()
                .categories(categories)
                .build();
    }

    public static ScrapCategoryListResponse toScrapCategoryListResponse(List<ScrapCategoryResponse> categories) {
        return ScrapCategoryListResponse.builder()
                .categories(categories)
                .build();
    }

}
