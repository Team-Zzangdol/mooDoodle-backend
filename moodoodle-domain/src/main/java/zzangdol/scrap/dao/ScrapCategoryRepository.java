package zzangdol.scrap.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zzangdol.scrap.domain.ScrapCategory;

public interface ScrapCategoryRepository extends JpaRepository<ScrapCategory, Long> {
}
