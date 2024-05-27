package zzangdol.scrap.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zzangdol.global.annotation.ApiErrorCodeExample;
import zzangdol.global.annotation.AuthUser;
import zzangdol.response.ResponseDto;
import zzangdol.response.status.ErrorStatus;
import zzangdol.scrap.business.CategoryFacade;
import zzangdol.scrap.presentation.dto.request.CategoryCreateRequest;
import zzangdol.scrap.presentation.dto.response.CategoryListResponse;
import zzangdol.user.domain.User;

@RequiredArgsConstructor
@ApiResponse(responseCode = "2000", description = "성공")
@Tag(name = "5️⃣ Category API", description = "카테고리 API")
@RequestMapping("/api/categories")
@RestController
public class CategoryController {

    private final CategoryFacade categoryFacade;


    @ApiErrorCodeExample({
            ErrorStatus.INTERNAL_SERVER_ERROR
    })
    @Operation(
            summary = "카테고리 생성 🔑",
            description = "새로운 카테고리를 생성합니다. name의 최대 길이는 15입니다."
    )
    @PostMapping
    public ResponseDto<Long> createDiary(@AuthUser User user, @Valid @RequestBody CategoryCreateRequest request) {
        Long diary = categoryFacade.createCategory(user, request);
        return ResponseDto.onSuccess(diary);
    }

    @ApiErrorCodeExample({
            ErrorStatus.INTERNAL_SERVER_ERROR
    })
    @Operation(
            summary = "카테고리 목록 조회 🔑",
            description = "사용자의 카테고리 목록을 반환합니다. 카테고리가 존재하지 않으면 빈 리스트를 반환합니다."
    )
    @GetMapping
    public ResponseDto<CategoryListResponse> getMonthlyDiaries(@AuthUser User user) {
        return ResponseDto.onSuccess(categoryFacade.getCategoriesByUser(user));
    }

}
