package zzangdol.report.presentation.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponse {

    private Long id;
    private AssetResponse asset;
    private List<ReportEmotionResponse> emotions;
    private double positivePercentage;
    private double negativePercentage;

}
