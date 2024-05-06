package zzangdol.report.dao.querydsl;

import java.util.Optional;
import zzangdol.report.domain.Asset;

public interface AssetQueryRepository {

    Optional<Asset> findRandomAsset();

}
