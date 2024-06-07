package zzangdol.feign.model.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "text2ImageModelClient", url = "${outer-api.text-2-image.url}")
public interface Text2ImageModelClient {
}