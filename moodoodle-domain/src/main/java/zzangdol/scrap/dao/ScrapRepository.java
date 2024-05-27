package zzangdol.scrap.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zzangdol.scrap.dao.querydsl.ScrapQueryRepository;
import zzangdol.scrap.domain.Scrap;
import zzangdol.user.domain.User;

public interface ScrapRepository extends JpaRepository<Scrap, Long>, ScrapQueryRepository {

    void deleteByUser(User user);

}
