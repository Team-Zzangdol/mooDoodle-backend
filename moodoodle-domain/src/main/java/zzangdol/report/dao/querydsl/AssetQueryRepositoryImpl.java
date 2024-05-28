package zzangdol.report.dao.querydsl;

import static zzangdol.report.domain.QAsset.asset;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import zzangdol.report.domain.Asset;


@Repository
@RequiredArgsConstructor
public class AssetQueryRepositoryImpl implements AssetQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Asset> findRandomAsset() {
        int count = queryFactory.selectFrom(asset).fetch().size();

        if (count > 0) {
            int index = (int) (Math.random() * count);
            Asset selectedAsset = queryFactory.selectFrom(asset)
                    .offset(index)
                    .limit(1)
                    .fetchOne();
            return Optional.ofNullable(selectedAsset);
        }

        return Optional.empty();
    }
}
