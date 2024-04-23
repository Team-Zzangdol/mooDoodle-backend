package zzangdol.scrap.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zzangdol.scrap.domain.Scrap;
import zzangdol.user.domain.User;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {

    void deleteByUser(User user);

}
