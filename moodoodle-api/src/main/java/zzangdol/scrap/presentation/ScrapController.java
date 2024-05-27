package zzangdol.scrap.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zzangdol.global.annotation.ApiErrorCodeExample;
import zzangdol.global.annotation.AuthUser;
import zzangdol.response.ResponseDto;
import zzangdol.response.status.ErrorStatus;
import zzangdol.scrap.business.ScrapFacade;
import zzangdol.user.domain.User;

@RequiredArgsConstructor
@ApiResponse(responseCode = "2000", description = "성공")
@Tag(name = "4️⃣ Scrap API", description = "스크랩 API")
@RequestMapping("/api/scraps")
@RestController
public class ScrapController {

    private final ScrapFacade scrapFacade;

    @ApiErrorCodeExample({
            ErrorStatus.INTERNAL_SERVER_ERROR,
            ErrorStatus.DIARY_NOT_FOUND
    })
    @Operation(
            summary = "스크랩 생성 🔑",
            description = "새로운 스크랩을 생성합니다."
    )
    @PostMapping
    public ResponseDto<Long> createScrap(@AuthUser User user, @Valid @RequestParam("diaryId") Long diaryId) {
        return ResponseDto.onSuccess(scrapFacade.createScrap(user, diaryId));
    }

    @ApiErrorCodeExample({
            ErrorStatus.INTERNAL_SERVER_ERROR,
            ErrorStatus.SCRAP_NOT_FOUND
    })
    @Operation(
            summary = "스크랩 삭제 🔑",
            description = "스크랩을 삭제합니다."
    )
    @DeleteMapping("/{scrapId}")
    public ResponseDto<Boolean> deleteScrap(@AuthUser User user, @PathVariable("scrapId") Long scrapId) {
        return ResponseDto.onSuccess(scrapFacade.deleteScrap(user, scrapId));
    }

    @ApiErrorCodeExample({
            ErrorStatus.INTERNAL_SERVER_ERROR,
            ErrorStatus.SCRAP_NOT_FOUND,
            ErrorStatus.CATEGORY_NOT_FOUND
    })
    @Operation(
            summary = "스크랩에 카테고리 추가 🔑",
            description = "기존 스크랩에 카테고리를 추가합니다."
    )
    @PostMapping("/{scrapId}/categories")
    public ResponseDto<Void> addCategoryToScrap(@AuthUser User user,
                                                @PathVariable("scrapId") Long scrapId,
                                                @RequestParam("categoryId") Long categoryId) {
        scrapFacade.addCategoryToScrap(user, scrapId, categoryId);
        return ResponseDto.onSuccess();
    }

    @ApiErrorCodeExample({
            ErrorStatus.INTERNAL_SERVER_ERROR,
            ErrorStatus.SCRAP_NOT_FOUND
    })
    @Operation(
            summary = "특정 다이어리의 스크랩 ID 반환 🔑",
            description = "특정 사용자가 특정 다이어리를 스크랩한 경우 스크랩 ID를 반환합니다."
    )
    @GetMapping("/{diaryId}")
    public ResponseDto<Long> getScrapId(@AuthUser User user, @PathVariable("diaryId") Long diaryId) {
        return ResponseDto.onSuccess(scrapFacade.getScrapByUserAndDiary(user, diaryId));
    }

}
