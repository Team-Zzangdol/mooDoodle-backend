package zzangdol.moodoodleapi.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import zzangdol.moodoodlecommon.exception.GeneralException;
import zzangdol.moodoodlecommon.response.ApiResponse;
import zzangdol.moodoodlecommon.response.status.ErrorStatus;

@RestController
public class HealthCheckController {

    @GetMapping("/health-check")
    public ApiResponse<Boolean> healthCheck() {
        return ApiResponse.onSuccess(Boolean.TRUE);
    }

}
