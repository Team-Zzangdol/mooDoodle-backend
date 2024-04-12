package zzangdol.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import zzangdol.feign.dto.ContentDto;
import zzangdol.feign.dto.MessageDto;

@FeignClient(name = "textEmotionAnalysisModelClient", url = "${outer-api.text-emotion-analysis.url}")
public interface TextEmotionAnalysisModelClient {

    @GetMapping
    MessageDto getMessage(@RequestBody ContentDto request);

}