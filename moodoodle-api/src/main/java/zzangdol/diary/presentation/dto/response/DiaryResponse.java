package zzangdol.diary.presentation.dto.response;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zzangdol.emotion.presentation.dto.response.EmotionResponse;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiaryResponse {

    private Long id;
    private LocalDate date;
    private String content;
    private String imageUrl;
    private String color;
    private DayOfWeek dayOfWeek;
    private Boolean isScrapped;
    private List<EmotionResponse> emotions;

}
