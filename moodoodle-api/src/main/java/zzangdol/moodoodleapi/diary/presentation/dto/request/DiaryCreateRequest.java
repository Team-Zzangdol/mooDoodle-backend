package zzangdol.moodoodleapi.diary.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiaryCreateRequest {

    @Schema(example = "2024-04-21T21:10", type = "string")
    private LocalDateTime date;

    @Schema(example = "오늘은 오랜만에 친구들과 놀이공원에 다녀왔다. 정말 신나는 하루였다. 집에 돌아오는 길에는 조금 허전하고 아쉬운 마음도 들었지만, 오늘 하루 친구들과 함께 할 수 있어서 정말 행복했다.")
    private String content;

    @Schema(example = "https://github.com/ahnsugyeong/ahnsugyeong/assets/88311377/5913c88f-ecde-495c-8c36-71a43e1c8a8d")
    private String imageUrl;

}
