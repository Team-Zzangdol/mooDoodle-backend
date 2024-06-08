package zzangdol.feign.model.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import zzangdol.feign.model.dto.AudioResponse;
import zzangdol.feign.model.dto.ContentRequest;
import zzangdol.feign.model.dto.ImageResponse;

@FeignClient(name = "text2ImageModelClient", url = "${outer-api.text-2-image.url}")
public interface Text2ImageModelClient {

    @PostMapping("/image")
    ImageResponse generateImages(@RequestBody ContentRequest request);

    @PostMapping("/audio")
    AudioResponse generateAudios(@RequestBody ContentRequest request);

}