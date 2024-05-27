package zzangdol.scrap.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScrapCategoryResponse {

    private Long id;
    private String name;
    private String imageUrl;
    private int scrapCount;
    private boolean isScraped;

}
