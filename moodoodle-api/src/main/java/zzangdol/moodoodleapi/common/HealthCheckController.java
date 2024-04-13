package zzangdol.moodoodleapi.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import zzangdol.moodoodlecommon.response.ResponseDto;

@RestController
public class HealthCheckController {

    @GetMapping("/health-check")
    public ResponseDto<Boolean> healthCheck() {
        return ResponseDto.onSuccess(Boolean.TRUE);
    }

}
