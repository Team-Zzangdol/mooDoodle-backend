package zzangdol.diary.business;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import zzangdol.diary.domain.Diary;
import zzangdol.diary.presentation.dto.response.DiaryListResponse;
import zzangdol.diary.presentation.dto.response.DiaryResponse;
import zzangdol.diary.presentation.dto.response.DiarySummaryResponse;
import zzangdol.diary.presentation.dto.response.ImageListResponse;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DiaryMapper {

    public static DiaryResponse toDiaryResponse(Diary diary) {
        return DiaryResponse.builder()
                .id(diary.getId())
                .date(diary.getDate())
                .content(diary.getContent())
                .imageUrl(diary.getPainting().getImageUrl())
                .color(diary.getPainting().getColor())
                .build();
    }

    public static DiarySummaryResponse toDiarySummaryResponse(Diary diary) {
        return DiarySummaryResponse.builder()
                .date(diary.getDate())
                .id(diary.getId())
                .imageUrl(diary.getPainting().getImageUrl())
                .build();
    }

    public static DiaryListResponse toDiaryListResponse(List<Diary> diaries, int year, int month) {
        YearMonth ym = YearMonth.of(year, month);
        LocalDate start = ym.atDay(1);
        LocalDate end = ym.atEndOfMonth();

        Map<LocalDate, Diary> diaryMap = diaries.stream()
                .collect(Collectors.toMap(
                        diary -> diary.getDate(),
                        diary -> diary,
                        (existing, replacement) -> existing
                ));

        List<DiarySummaryResponse> diarySummaryResponses = IntStream.rangeClosed(1, ym.lengthOfMonth())
                .mapToObj(day -> start.plusDays(day - 1))
                .map(date -> diaryMap.containsKey(date) ? toDiarySummaryResponse(diaryMap.get(date))
                        : new DiarySummaryResponse(date.atStartOfDay().toLocalDate(), null, null))
                .collect(Collectors.toList());

        return DiaryListResponse.builder()
                .diaries(diarySummaryResponses)
                .build();
    }

    public static ImageListResponse toImageResponse(List<String> imageUrls) {
        return ImageListResponse.builder()
                .imageUrls(imageUrls)
                .build();
    }


}
