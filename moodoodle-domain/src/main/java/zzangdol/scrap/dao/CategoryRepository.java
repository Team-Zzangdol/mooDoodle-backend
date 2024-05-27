package zzangdol.scrap.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import zzangdol.scrap.domain.Category;
import zzangdol.user.domain.User;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findDiariesByUser(User user);

    void deleteByUser(User user);

}
