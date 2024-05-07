package zzangdol.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import zzangdol.diary.dao.querydsl.DiaryQueryRepository;
import zzangdol.diary.dao.querydsl.DiaryQueryRepositoryImpl;
import zzangdol.report.dao.querydsl.AssetQueryRepository;
import zzangdol.report.dao.querydsl.AssetQueryRepositoryImpl;

@TestConfiguration
public class TestQueryDslConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public DiaryQueryRepository diaryQueryRepository() {
        return new DiaryQueryRepositoryImpl(jpaQueryFactory());
    }

    @Bean
    public AssetQueryRepository assetQueryRepository() {
        return new AssetQueryRepositoryImpl(jpaQueryFactory());
    }

}
