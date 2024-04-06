package zzangdol.scrap.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zzangdol.scrap.domain.Scrap;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
}
