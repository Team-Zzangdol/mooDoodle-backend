package zzangdol.scrap.implement;

import java.util.List;
import zzangdol.diary.domain.Diary;
import zzangdol.scrap.domain.Category;
import zzangdol.scrap.presentation.dto.response.CategoryResponse;
import zzangdol.scrap.presentation.dto.response.ScrapCategoryResponse;
import zzangdol.user.domain.User;

public interface CategoryQueryService {

    List<Category> getCategoriesByUser(User user);

    List<CategoryResponse> getCategoryResponsesByUser(User user);

    List<ScrapCategoryResponse> getScrapCategoryResponsesByUser(User user, Long diaryId);

    List<Diary> getDiariesByCategory(User user, Long categoryId);

}
