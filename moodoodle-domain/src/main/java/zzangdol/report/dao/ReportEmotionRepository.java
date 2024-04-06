package zzangdol.report.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zzangdol.report.domain.ReportEmotion;

public interface ReportEmotionRepository extends JpaRepository<ReportEmotion, Long> {
}
