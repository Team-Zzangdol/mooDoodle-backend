package zzangdol.diary.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zzangdol.global.BaseTimeEntity;
import zzangdol.user.domain.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Diary extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Embedded
    private Painting painting;


    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiaryEmotion> diaryEmotions = new ArrayList<>();

    @Builder
    public Diary(LocalDate date, String content, User user, Painting painting) {
        this.date = date;
        this.content = content;
        this.user = user;
        this.painting = painting;
    }

    public void updateDate(LocalDate date) {
        if (date != null) {
            this.date = date;
        }
    }

    public void updateContent(String content) {
        if (content != null) {
            this.content = content;
        }
    }

}
