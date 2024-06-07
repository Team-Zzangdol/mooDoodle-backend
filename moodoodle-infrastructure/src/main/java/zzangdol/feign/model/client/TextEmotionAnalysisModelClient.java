package zzangdol.feign.model.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import zzangdol.feign.model.dto.ContentRequest;
import zzangdol.feign.model.dto.EmotionResponse;

@FeignClient(name = "textEmotionAnalysisModelClient", url = "${outer-api.text-emotion-analysis.url}")
public interface TextEmotionAnalysisModelClient {

    @PostMapping
    EmotionResponse analyzeEmotion(@RequestBody ContentRequest request);

}
