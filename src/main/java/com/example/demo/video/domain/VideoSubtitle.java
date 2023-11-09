package com.example.demo.video.domain;

import com.example.demo.config.utils.BaseTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE subtitles SET deleted_at = CURRENT_TIMESTAMP, is_deleted = TRUE where id = ?")
@Entity
@Table(name = "video_subtitles")
public class VideoSubtitle extends BaseTime {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id")
    private Video video;

    @Column(nullable = false)
    private String korSubtitleContent;

    @Column(nullable = false)
    private String engSubtitleContent;

    @Column(nullable = false)
    private String korStartTime;

    @Column(nullable = false)
    private String engStartTime;

    @Column(nullable = false)
    private String korEndTime;

    @Column(nullable = false)
    private String engEndTime;

    @Builder
    public VideoSubtitle(Video video, String korSubtitleContent, String engSubtitleContent, String korStartTime, String engStartTime, String korEndTime, String engEndTime) {
        this.video = video;
        this.korSubtitleContent = korSubtitleContent;
        this.engSubtitleContent = engSubtitleContent;
        this.korStartTime = korStartTime;
        this.engStartTime = engStartTime;
        this.korEndTime = korEndTime;
        this.engEndTime = engEndTime;
    }
}
