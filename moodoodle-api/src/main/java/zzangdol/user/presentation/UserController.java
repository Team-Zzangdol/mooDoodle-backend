package zzangdol.user.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zzangdol.FCMService;
import zzangdol.global.annotation.AuthUser;
import zzangdol.response.ResponseDto;
import zzangdol.user.business.UserFacade;
import zzangdol.user.domain.User;
import zzangdol.user.presentation.dto.request.PushNotificationRequest;
import zzangdol.user.presentation.dto.request.TestPushNotificationRequest;
import zzangdol.user.presentation.dto.request.UserInfoUpdateRequest;
import zzangdol.user.presentation.dto.response.UserInfoResponse;

@RequiredArgsConstructor
@ApiResponse(responseCode = "2000", description = "성공")
@Tag(name = "2️⃣ User API", description = "사용자 API")
@RequestMapping("/api/users")
@RestController
public class UserController {

    private final UserFacade userFacade;
    private final FCMService fcmService;

    @Operation(
            summary = "사용자 정보 조회 🔑",
            description = "Access Token 을 통해 사용자의 정보를 조회합니다."
    )
    @GetMapping
    public ResponseDto<UserInfoResponse> getUserInfo(@AuthUser User user) {
        return ResponseDto.onSuccess(userFacade.getUserInfo(user));
    }

    @Operation(
            summary = "사용자 정보 수정 🔑",
            description = "사용자의 정보를 수정합니다."
    )
    @PatchMapping
    public ResponseDto<UserInfoResponse> updateUserInfo(@AuthUser User user, @RequestBody UserInfoUpdateRequest request) {
        return ResponseDto.onSuccess(userFacade.updateUserInfo(user, request));
    }

    @Operation(
            summary = "Push 알림 허용 / 거부 🔑",
            description = "사용자가 Push 알림을 허용하거나 거부합니다."
    )
    @PatchMapping("/push-notifications")
    public ResponseDto<Void> handlePushNotifications(@AuthUser User user, @RequestBody PushNotificationRequest request) {
        userFacade.handlePushNotifications(user, request);
        return ResponseDto.onSuccess();
    }

    @Operation(
            summary = "[테스트] 푸시 알림 전송",
            description = "특정 사용자의 FCM 토큰으로 푸시 알림을 즉시 전송합니다."
    )
    @PostMapping("/send-test-notification")
    public ResponseDto<Void> sendTestNotification(@RequestBody TestPushNotificationRequest request) {
        try {
            fcmService.sendNotification(request.getFcmToken(), request.getTitle(), request.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseDto.onSuccess();
    }

    @Operation(
            summary = "회원 탈퇴 🔑",
            description = "현재 로그인된 사용자의 계정을 탈퇴하고, 모든 관련 데이터를 삭제합니다.")
    @DeleteMapping("/withdraw")
    public ResponseDto<Boolean> withDrawUser(@AuthUser User user) {
        return ResponseDto.onSuccess(userFacade.withDrawUser(user));
    }

}
