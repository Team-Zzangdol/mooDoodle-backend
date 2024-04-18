package zzangdol.feign.model.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import zzangdol.outer.api.model.dto.ContentDto;
import zzangdol.outer.api.model.dto.MessageDto;

@FeignClient(name = "textEmotionAnalysisModelClient", url = "${outer-api.text-emotion-analysis.url}")
public interface TextEmotionAnalysisModelClient {

    @GetMapping
    MessageDto getMessage(@RequestBody ContentDto request);

}
