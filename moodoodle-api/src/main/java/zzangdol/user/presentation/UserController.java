package zzangdol.user.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zzangdol.global.annotation.AuthUser;
import zzangdol.user.business.UserFacade;
import zzangdol.user.presentation.dto.response.UserInfoResponse;
import zzangdol.response.ResponseDto;
import zzangdol.user.domain.User;

@RequiredArgsConstructor
@ApiResponse(responseCode = "2000", description = "성공")
@Tag(name = "2️⃣ User API", description = "사용자 API")
@RequestMapping("/api/users")
@RestController
public class UserController {

    private final UserFacade userFacade;

    @Operation(
            summary = "사용자 정보 조회 🔑",
            description = "Access Token 을 통해 사용자의 정보를 조회합니다."
    )
    @GetMapping
    public ResponseDto<UserInfoResponse> getUserInfo(@AuthUser User user) {
        return ResponseDto.onSuccess(userFacade.getUserInfo(user));
    }

}
