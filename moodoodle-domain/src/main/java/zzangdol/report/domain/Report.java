package zzangdol.report.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zzangdol.global.BaseTimeEntity;
import zzangdol.user.domain.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Report extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "asset_id")
    private Asset asset;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportEmotion> reportEmotions = new ArrayList<>();

    private double positivePercentage;
    private double negativePercentage;

    private LocalDate startDate;
    private LocalDate endDate;

    @Builder
    public Report(User user, Asset asset,
                  double positivePercentage, double negativePercentage,
                  LocalDate startDate, LocalDate endDate) {
        this.user = user;
        this.asset = asset;
        this.positivePercentage = positivePercentage;
        this.negativePercentage = negativePercentage;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void addReportEmotion(ReportEmotion reportEmotion) {
        this.reportEmotions.add(reportEmotion);
    }

}
