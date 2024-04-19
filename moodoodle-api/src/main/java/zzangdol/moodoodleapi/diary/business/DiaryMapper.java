package zzangdol.moodoodleapi.diary.business;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import zzangdol.diary.domain.Diary;
import zzangdol.moodoodleapi.diary.presentation.dto.response.DiaryDetailResponse;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DiaryMapper {

    public static DiaryDetailResponse toDiaryDetailResponse(Diary diary) {
        return DiaryDetailResponse.builder()
                .id(diary.getId())
                .date(diary.getDate())
                .content(diary.getContent())
                .imageUrl(diary.getPainting().getImageUrl())
                .color(diary.getPainting().getColor())
                .build();
    }

}
