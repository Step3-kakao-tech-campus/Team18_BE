package com.example.demo.video;

import com.example.demo.config.utils.BaseTime;
import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE subtitles SET deleted_at = CURRENT_TIMESTAMP, is_deleted = TRUE where id = ?")
@Table(name = "subtitles")
public class Subtitle extends BaseTime {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id")
    private Video video;

    @Column
    private String korSubtitleContent;

    @Column
    private String engSubtitleContent;

    @Column
    private String korStartTime;

    @Column
    private String engStartTime;

    @Column
    private String korEndTime;

    @Column
    private String engEndTime;

    @Builder
    public Subtitle(Video video, String korSubtitleContent, String engSubtitleContent, String korStartTime, String engStartTime, String korEndTime, String engEndTime) {
        this.video = video;
        this.korSubtitleContent = korSubtitleContent;
        this.engSubtitleContent = engSubtitleContent;
        this.korStartTime = korStartTime;
        this.engStartTime = engStartTime;
        this.korEndTime = korEndTime;
        this.engEndTime = engEndTime;
    }
}
