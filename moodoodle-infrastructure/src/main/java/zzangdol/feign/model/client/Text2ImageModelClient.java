package zzangdol.feign.model.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import zzangdol.outer.api.model.dto.ContentDto;
import zzangdol.outer.api.model.dto.MessageDto;

@FeignClient(name = "text2ImageModelClient", url = "${outer-api.text-2-image.url}")
public interface Text2ImageModelClient {

    @GetMapping
    MessageDto getMessage(@RequestBody ContentDto request);

}
