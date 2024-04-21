package zzangdol.moodoodleapi.diary.business;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import zzangdol.diary.domain.Diary;
import zzangdol.moodoodleapi.diary.presentation.dto.response.DiaryListResponse;
import zzangdol.moodoodleapi.diary.presentation.dto.response.DiaryResponse;

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

    public static DiaryListResponse toDiaryListResponse(List<Diary> diaries) {
        List<DiaryResponse> diaryResponses = diaries.stream()
                .map(DiaryMapper::toDiaryResponse)
                .collect(Collectors.toList());

        return DiaryListResponse.builder()
                .diaries(diaryResponses)
                .build();
    }


}
