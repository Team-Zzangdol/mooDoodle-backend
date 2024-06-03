package zzangdol.report.business;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import zzangdol.report.domain.Asset;
import zzangdol.report.domain.Report;
import zzangdol.report.domain.ReportEmotion;
import zzangdol.report.presentation.dto.response.AssetResponse;
import zzangdol.report.presentation.dto.response.LatestReportResponse;
import zzangdol.report.presentation.dto.response.ReportEmotionResponse;
import zzangdol.report.presentation.dto.response.ReportResponse;

public class ReportMapper {

    private static final Map<Integer, String> weekNumberToKorean = new HashMap<>();

    static {
        weekNumberToKorean.put(1, "첫째 주");
        weekNumberToKorean.put(2, "둘째 주");
        weekNumberToKorean.put(3, "셋째 주");
        weekNumberToKorean.put(4, "넷째 주");
        weekNumberToKorean.put(5, "다섯째 주");
    }

    private static AssetResponse toAssetResponse(Asset asset) {
        return AssetResponse.builder()
                .url(asset.getUrl())
                .description(asset.getDescription())
                .build();
    }

    private static ReportEmotionResponse toReportEmotionResponse(ReportEmotion reportEmotion) {
        return ReportEmotionResponse.builder()
                .name(reportEmotion.getEmotion().getName())
                .percentage(reportEmotion.getPercentage())
                .build();
    }

    private static List<ReportEmotionResponse> toReportEmotionResponseList(List<ReportEmotion> reportEmotions) {
        return reportEmotions.stream()
                .map(ReportMapper::toReportEmotionResponse)
                .collect(Collectors.toList());
    }

    public static ReportResponse toReportResponse(Report report, Long prevReportId, Long nextReportId) {
        LocalDate endDate = report.getEndDate();
        WeekFields weekFields = WeekFields.ISO;
        int weekOfMonth = endDate.get(weekFields.weekOfMonth());
        int month = endDate.getMonthValue();

        // 목요일 기준으로 주의 월을 결정하는 로직
        LocalDate thursdayOfCurrentWeek = endDate.with(DayOfWeek.THURSDAY);
        if (thursdayOfCurrentWeek.getMonthValue() != endDate.getMonthValue()) {
            month = thursdayOfCurrentWeek.getMonthValue();
            weekOfMonth = thursdayOfCurrentWeek.get(weekFields.weekOfMonth());
        }

        String weekKorean = weekNumberToKorean.getOrDefault(weekOfMonth, weekOfMonth + "째 주");
        String reportWeek = String.format("%d월 %s", month, weekKorean);

        return ReportResponse.builder()
                .id(report.getId())
                .reportWeek(reportWeek)
                .prevReportId(prevReportId)
                .nextReportId(nextReportId)
                .asset(report.getAsset() != null ? toAssetResponse(report.getAsset()) : null)
                .emotions(toReportEmotionResponseList(report.getReportEmotions()))
                .positivePercentage(report.getPositivePercentage())
                .negativePercentage(report.getNegativePercentage())
                .build();
    }

    public static LatestReportResponse toLatestReportResponse(Report report, Boolean isRead) {
        return LatestReportResponse.builder()
                .reportId(report.getId())
                .isRead(isRead)
                .build();
    }
}
