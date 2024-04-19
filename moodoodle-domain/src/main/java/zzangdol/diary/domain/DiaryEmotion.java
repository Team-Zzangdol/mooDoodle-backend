package zzangdol.diary.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zzangdol.emotion.domain.Emotion;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class DiaryEmotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emotion_id")
    private Emotion emotion;

    @Builder
    public DiaryEmotion(Diary diary, Emotion emotion) {
        this.diary = diary;
        this.emotion = emotion;
    }

    // 연관관계 편의 메서드
    public void addDiaryEmotion(Diary diary) {
        diary.getDiaryEmotions().add(this);
    }

    public void removeDiaryEmotion() {
        if (diary != null) {
            diary.getDiaryEmotions().remove(this);
            diary = null;
        }
    }
}
