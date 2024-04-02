package zzangdol.moodoodleapi.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import zzangdol.moodoodlecommon.response.ApiResponse;

@RestController
public class HealthCheckController {

    @GetMapping("/health-check")
    public ApiResponse<Boolean> healthCheck() {
        return ApiResponse.onSuccess(Boolean.TRUE);
    }

}
