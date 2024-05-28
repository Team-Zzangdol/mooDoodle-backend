package zzangdol.scrap.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import zzangdol.scrap.dao.querydsl.CategoryQueryRepository;
import zzangdol.scrap.domain.Category;
import zzangdol.user.domain.User;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryQueryRepository {

    List<Category> findCategoriesByUser(User user);

    void deleteByUser(User user);

    Optional<Category> findCategoryByUserAndName(User user, String name);

}
