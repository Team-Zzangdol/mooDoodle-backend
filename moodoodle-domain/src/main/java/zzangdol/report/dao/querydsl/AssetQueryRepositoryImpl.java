package zzangdol.report.dao.querydsl;

import static zzangdol.report.domain.QAsset.asset;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import zzangdol.report.domain.Asset;


@Repository
@RequiredArgsConstructor
public class AssetQueryRepositoryImpl implements AssetQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Asset> findRandomAssetExcludingId(Long excludedId) {
        List<Asset> assets = queryFactory.selectFrom(asset)
                .where(asset.id.ne(excludedId))
                .fetch();

        if (assets.size() > 0) {
            int index = (int) (Math.random() * assets.size());
            return Optional.of(assets.get(index));
        }

        return Optional.empty();
    }
}
