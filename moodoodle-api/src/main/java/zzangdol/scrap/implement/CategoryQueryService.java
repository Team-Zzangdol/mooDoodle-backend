package zzangdol.scrap.implement;

import java.util.List;
import zzangdol.scrap.domain.Category;
import zzangdol.user.domain.User;

public interface CategoryQueryService {

    List<Category> getCategoriesByUser(User user);

}
