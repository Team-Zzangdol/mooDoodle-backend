package zzangdol.scrap.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import zzangdol.scrap.domain.Category;
import zzangdol.scrap.domain.Scrap;
import zzangdol.scrap.domain.ScrapCategory;

public interface ScrapCategoryRepository extends JpaRepository<ScrapCategory, Long> {

    Optional<ScrapCategory> findScrapCategoryByScrapAndCategory(Scrap scrap, Category category);

}
