package zzangdol.moodoodleapi.diary.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zzangdol.moodoodleapi.diary.business.DiaryFacade;
import zzangdol.moodoodleapi.diary.presentation.dto.request.DiaryCreateRequest;
import zzangdol.moodoodleapi.diary.presentation.dto.request.DiaryUpdateRequest;
import zzangdol.moodoodleapi.global.annotation.ApiErrorCodeExample;
import zzangdol.moodoodleapi.global.annotation.AuthUser;
import zzangdol.moodoodlecommon.response.ResponseDto;
import zzangdol.moodoodlecommon.response.status.ErrorStatus;
import zzangdol.user.domain.User;

@RequiredArgsConstructor
@ApiResponse(responseCode = "2000", description = "성공")
@Tag(name = "3️⃣ Diary API", description = "일기 API")
@RequestMapping("/api/diary")
@RestController
public class DiaryController {

    private final DiaryFacade diaryFacade;

    @ApiErrorCodeExample({
            ErrorStatus.EMAIL_ALREADY_EXISTS,
            ErrorStatus.INTERNAL_SERVER_ERROR
    })
    @Operation(summary = "일기 생성", description = "일기를 생성합니다.")
    @PostMapping
    public ResponseDto<Long> createDiary(@AuthUser User user, @RequestBody DiaryCreateRequest request) {
        return ResponseDto.onSuccess(diaryFacade.createDiary(user, request));
    }

    @ApiErrorCodeExample({
            ErrorStatus.EMAIL_ALREADY_EXISTS,
            ErrorStatus.DIARY_NOT_FOUND,
            ErrorStatus.INTERNAL_SERVER_ERROR
    })
    @Operation(summary = "일기 수정", description = "일기를 수정합니다.")
    @PatchMapping("/{diaryId}")
    public ResponseDto<Long> updateDiary(@AuthUser User user,
                                         @PathVariable("diaryId") Long diaryId,
                                         @RequestBody DiaryUpdateRequest request) {
        return ResponseDto.onSuccess(diaryFacade.updateDiary(user, diaryId, request));
    }

}
