package zzangdol.diary.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiaryUpdateRequest {

    @Schema(example = "2024-04-20", type = "string")
    private LocalDate date;

    @Schema(example = "오늘은 오랜만에 친구들과 놀이공원에 다녀왔다. 츄러스도 먹고 정말 신나는 하루였다. 집에 돌아오는 길에는 조금 허전하고 아쉬운 마음도 들었지만, 오늘 하루 친구들과 함께 할 수 있어서 정말 행복했다.")
    private String content;

}
