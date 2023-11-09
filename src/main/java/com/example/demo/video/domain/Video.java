package com.example.demo.video.domain;

import com.example.demo.config.utils.BaseTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE videos SET deleted_at = CURRENT_TIMESTAMP, is_deleted = TRUE where id = ?")
@Entity
@Table(name = "videos")
public class Video extends BaseTime {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Column(nullable = false)
    private String videoUrl;

    @Column(nullable = false)
    private String videoTitleKorean;

    @Column(nullable = false)
    private String videoTitleEng;

    @Column(nullable = false)
    private String videoIntroduction;

    @Column(nullable = false)
    private String videoStartTime;

    @Column(nullable = false)
    private String videoEndTime;

    @Column(nullable = false)
    @ColumnDefault("0")
    private long viewCount;

    @Column(nullable = false)
    private String videoThumbnailUrl;

    @Builder
    public Video(String videoUrl, String videoTitleKorean, String videoTitleEng, String videoIntroduction, String videoStartTime, String videoEndTime, long viewCount, String videoThumbnailUrl) {
        this.videoUrl = videoUrl;
        this.videoTitleKorean = videoTitleKorean;
        this.videoTitleEng = videoTitleEng;
        this.videoIntroduction = videoIntroduction;
        this.videoStartTime = videoStartTime;
        this.videoEndTime = videoEndTime;
        this.viewCount = viewCount;
        this.videoThumbnailUrl = videoThumbnailUrl;
    }

    public void updateViewCount(){
        this.viewCount += 1;
    }
}
