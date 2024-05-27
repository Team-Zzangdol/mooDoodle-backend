package zzangdol.scrap.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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


}
