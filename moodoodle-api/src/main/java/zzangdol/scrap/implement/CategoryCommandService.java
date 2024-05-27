package zzangdol.scrap.implement;

import zzangdol.scrap.domain.Category;
import zzangdol.scrap.presentation.dto.request.CategoryCreateRequest;
import zzangdol.user.domain.User;

public interface CategoryCommandService {

    Category createCategory(User user, CategoryCreateRequest request);

}
