package zzangdol.scrap.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zzangdol.scrap.domain.Category;
import zzangdol.user.domain.User;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    void deleteByUser(User user);

}
