package zzangdol.moodoodleapi.user.presentation;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zzangdol.moodoodleapi.global.annotation.AuthUser;
import zzangdol.moodoodleapi.user.business.UserFacade;
import zzangdol.moodoodleapi.user.presentation.dto.response.UserInfoResponse;
import zzangdol.moodoodlecommon.response.ResponseDto;
import zzangdol.user.domain.User;

@RequiredArgsConstructor
@ApiResponse(responseCode = "2000", description = "성공")
@Tag(name = "2️⃣ User API", description = "사용자 API")
@RequestMapping("/api/users")
@RestController
public class UserController {

    private final UserFacade userFacade;

    @GetMapping
    public ResponseDto<UserInfoResponse> getUserInfo(@AuthUser User user) {
        return ResponseDto.onSuccess(userFacade.getUserInfo(user));
    }

}
