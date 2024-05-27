package zzangdol.scrap.implement;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zzangdol.scrap.dao.CategoryRepository;
import zzangdol.scrap.domain.Category;
import zzangdol.user.domain.User;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CategoryQueryServiceImpl implements CategoryQueryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getCategoriesByUser(User user) {
        return categoryRepository.findDiariesByUser(user);
    }

}
