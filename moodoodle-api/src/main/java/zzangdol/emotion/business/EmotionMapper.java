package zzangdol.emotion.business;

import zzangdol.emotion.domain.Emotion;
import zzangdol.emotion.presentation.dto.response.EmotionResponse;

public class EmotionMapper {

    public static EmotionResponse toEmotionResponse(Emotion emotion) {
        return EmotionResponse.builder()
                .name(emotion.getName())
                .imageUrl(emotion.getImageUrl())
                .build();
    }

}
