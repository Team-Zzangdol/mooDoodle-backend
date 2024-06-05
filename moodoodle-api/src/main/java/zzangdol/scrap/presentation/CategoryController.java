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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zzangdol.diary.presentation.dto.response.CategoryDiaryListResponse;
import zzangdol.global.annotation.ApiErrorCodeExample;
import zzangdol.global.annotation.AuthUser;
import zzangdol.response.ResponseDto;
import zzangdol.response.status.ErrorStatus;
import zzangdol.scrap.business.CategoryFacade;
import zzangdol.scrap.presentation.dto.request.CategoryCreateRequest;
import zzangdol.scrap.presentation.dto.response.CategoryListResponse;
import zzangdol.scrap.presentation.dto.response.ScrapCategoryListResponse;
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
    public ResponseDto<Long> createCategory(@AuthUser User user, @Valid @RequestBody CategoryCreateRequest request) {
        Long diary = categoryFacade.createCategory(user, request);
        return ResponseDto.onSuccess(diary);
    }

    @ApiErrorCodeExample({
            ErrorStatus.INTERNAL_SERVER_ERROR
    })
    @Operation(
            summary = "[스크랩 탭] 카테고리 목록 조회 🔑",
            description = "사용자의 카테고리 목록을 반환합니다. 카테고리가 존재하지 않으면 빈 리스트를 반환합니다."
    )
    @GetMapping
    public ResponseDto<CategoryListResponse> getCategoriesByUser(@AuthUser User user) {
        return ResponseDto.onSuccess(categoryFacade.getCategoriesByUser(user));
    }

    @ApiErrorCodeExample({
            ErrorStatus.INTERNAL_SERVER_ERROR
    })
    @Operation(
            summary = "[바텀시트] 스크랩 카테고리 목록 조회 🔑",
            description = "사용자의 카테고리 목록을 반환합니다. 카테고리가 존재하지 않으면 빈 리스트를 반환합니다."
    )
    @GetMapping("/scraps")
    public ResponseDto<ScrapCategoryListResponse> getScrapCategoriesByUser(@AuthUser User user,
                                                                           @RequestParam Long diaryId) {
        return ResponseDto.onSuccess(categoryFacade.getScrapCategoriesByUser(user, diaryId));
    }

    @ApiErrorCodeExample({
            ErrorStatus.INTERNAL_SERVER_ERROR,
            ErrorStatus.CATEGORY_NOT_FOUND
    })
    @Operation(
            summary = "스크랩 카테고리에 속한 일기 목록 조회 🔑",
            description = "특정 스크랩 카테고리에 속한 일기 목록을 조회합니다."
    )
    @GetMapping("/{categoryId}/diaries")
    public ResponseDto<CategoryDiaryListResponse> getDiariesByCategory(@AuthUser User user,
                                                                       @PathVariable("categoryId") Long categoryId) {
        return ResponseDto.onSuccess(categoryFacade.getDiariesByCategory(user, categoryId));
    }

    @ApiErrorCodeExample({
            ErrorStatus.CATEGORY_NOT_FOUND,
            ErrorStatus.INTERNAL_SERVER_ERROR
    })
    @Operation(
            summary = "카테고리 삭제 🔑",
            description = "지정된 ID의 카테고리를 삭제합니다. 삭제 성공 시 true를 반환합니다."
    )
    @DeleteMapping("/{categoryId}")
    public ResponseDto<Boolean> deleteCategory(@AuthUser User user,
                                               @PathVariable("categoryId") Long categoryId) {
        categoryFacade.deleteCategory(user, categoryId);
        return ResponseDto.onSuccess(true);
    }

}
