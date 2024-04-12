package zzangdol.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import zzangdol.feign.dto.ContentDto;
import zzangdol.feign.dto.MessageDto;

@FeignClient(name = "text2ImageModelClient", url = "${outer-api.text-2-image.url}")
public interface Text2ImageModelClient {

    @GetMapping
    MessageDto getMessage(@RequestBody ContentDto request);

}
