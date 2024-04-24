package zzangdol.diary.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zzangdol.diary.business.DiaryFacade;
import zzangdol.diary.presentation.dto.request.DiaryCreateRequest;
import zzangdol.diary.presentation.dto.request.DiaryUpdateRequest;
import zzangdol.diary.presentation.dto.response.DiaryResponse;
import zzangdol.diary.presentation.dto.response.DiaryListResponse;
import zzangdol.global.annotation.ApiErrorCodeExample;
import zzangdol.global.annotation.AuthUser;
import zzangdol.response.ResponseDto;
import zzangdol.response.status.ErrorStatus;
import zzangdol.user.domain.User;

@RequiredArgsConstructor
@ApiResponse(responseCode = "2000", description = "성공")
@Tag(name = "3️⃣ Diary API", description = "일기 API")
@RequestMapping("/api/diaries")
@RestController
public class DiaryController {

    private final DiaryFacade diaryFacade;

    @ApiErrorCodeExample({
            ErrorStatus.INTERNAL_SERVER_ERROR
    })
    @Operation(
            summary = "일기 생성 🔑",
            description = "새로운 일기를 생성합니다."
    )
    @PostMapping
    public ResponseDto<Long> createDiary(@AuthUser User user, @Valid @RequestBody DiaryCreateRequest request) {
        Long diary = diaryFacade.createDiary(user, request);
        return ResponseDto.onSuccess(diary);
    }

    @ApiErrorCodeExample({
            ErrorStatus.DIARY_NOT_FOUND,
            ErrorStatus.INTERNAL_SERVER_ERROR
    })
    @Operation(
            summary = "일기 수정 🔑",
            description = "지정된 ID의 일기를 수정합니다."
    )
    @PatchMapping("/{diaryId}")
    public ResponseDto<Long> updateDiary(@AuthUser User user,
                                         @PathVariable("diaryId") Long diaryId,
                                         @RequestBody DiaryUpdateRequest request) {
        return ResponseDto.onSuccess(diaryFacade.updateDiary(user, diaryId, request));
    }

    @ApiErrorCodeExample({
            ErrorStatus.DIARY_NOT_FOUND,
            ErrorStatus.INTERNAL_SERVER_ERROR
    })
    @Operation(
            summary = "일기 삭제 🔑",
            description = "지정된 ID의 일기를 삭제합니다. 삭제 성공 시 true를 반환합니다."
    )
    @DeleteMapping("/{diaryId}")
    public ResponseDto<Boolean> deleteDiary(@AuthUser User user,
                                            @PathVariable("diaryId") Long diaryId) {
        diaryFacade.deleteDiary(user, diaryId);
        return ResponseDto.onSuccess(true);
    }

    @ApiErrorCodeExample({
            ErrorStatus.DIARY_NOT_FOUND,
            ErrorStatus.INTERNAL_SERVER_ERROR
    })
    @Operation(
            summary = "일기 단건 조회 🔑",
            description = "지정된 ID의 일기를 조회합니다. 상세 정보를 반환합니다."
    )
    @GetMapping("/{diaryId}")
    public ResponseDto<DiaryResponse> getDiaryByUser(@AuthUser User user,
                                                   @PathVariable("diaryId") Long diaryId) {
        return ResponseDto.onSuccess(diaryFacade.getDiaryByUser(user, diaryId));
    }

    @ApiErrorCodeExample({
            ErrorStatus.DIARY_NOT_FOUND,
            ErrorStatus.INTERNAL_SERVER_ERROR
    })
    @Operation(
            summary = "일기 월간 조회 🔑",
            description = "사용자가 지정한 연도와 월에 해당하는 모든 일기를 조회합니다. 일기 목록을 반환합니다."
    )
    @GetMapping
    public ResponseDto<DiaryListResponse> getMonthlyDiaries(@AuthUser User user,
                                                            @RequestParam("year") int year,
                                                            @RequestParam("month") int month) {
        return ResponseDto.onSuccess(diaryFacade.getMonthlyDiariesByUser(user, year, month));
    }

}