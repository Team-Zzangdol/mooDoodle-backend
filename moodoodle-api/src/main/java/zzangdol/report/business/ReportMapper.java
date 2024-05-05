package zzangdol.report.business;

import java.util.List;
import java.util.stream.Collectors;
import zzangdol.report.domain.Asset;
import zzangdol.report.domain.Report;
import zzangdol.report.domain.ReportEmotion;
import zzangdol.report.presentation.dto.response.AssetResponse;
import zzangdol.report.presentation.dto.response.ReportEmotionResponse;
import zzangdol.report.presentation.dto.response.ReportResponse;

public class ReportMapper {

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

    public static ReportResponse toReportResponse(Report report) {
        return ReportResponse.builder()
                .id(report.getId())
                .asset(toAssetResponse(report.getAsset()))
                .emotions(toReportEmotionResponseList(report.getReportEmotions()))
                .positivePercentage(report.getPositivePercentage())
                .negativePercentage(report.getNegativePercentage())
                .build();
    }

}
