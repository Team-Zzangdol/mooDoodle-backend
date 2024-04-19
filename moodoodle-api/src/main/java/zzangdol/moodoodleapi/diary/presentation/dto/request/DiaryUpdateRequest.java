package zzangdol.moodoodleapi.diary.presentation.dto.request;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiaryUpdateRequest {

    private LocalDateTime date;
    private String content;
    private String imageUrl;

}
