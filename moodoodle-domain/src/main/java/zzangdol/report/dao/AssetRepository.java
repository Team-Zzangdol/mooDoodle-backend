package zzangdol.report.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zzangdol.report.dao.querydsl.AssetQueryRepository;
import zzangdol.report.domain.Asset;

public interface AssetRepository extends JpaRepository<Asset, Long>, AssetQueryRepository {
}
