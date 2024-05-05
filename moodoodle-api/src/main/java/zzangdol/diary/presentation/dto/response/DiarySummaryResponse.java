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
public class DiarySummaryResponse {

    private LocalDate date;
    private Long id;
    private String imageUrl;

}
