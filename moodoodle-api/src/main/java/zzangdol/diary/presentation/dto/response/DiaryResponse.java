package zzangdol.diary.presentation.dto.response;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

}
