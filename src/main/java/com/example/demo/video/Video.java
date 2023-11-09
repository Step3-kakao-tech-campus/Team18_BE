package com.example.demo.video;

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
@Entity
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE videos SET deleted_at = CURRENT_TIMESTAMP, is_deleted = TRUE where id = ?")
@Table(name = "videos")
public class Video extends BaseTime {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Column(nullable = false)
    private String videoUrl;

    @Column
    private String videoTitleKorean;

    @Column
    private String videoTitleEng;

    @Column
    private String videoStartTime;

    @Column
    private String videoEndTime;

    @Column(nullable = false)
    @ColumnDefault("0")
    private long views;

    @Column
    private String videoThumbnailUrl;

    @Builder
    public Video(String videoUrl, String videoTitleKorean, String videoTitleEng, String videoStartTime, String videoEndTime, long views, String videoThumbnailUrl) {
        this.videoUrl = videoUrl;
        this.videoTitleKorean = videoTitleKorean;
        this.videoTitleEng = videoTitleEng;
        this.videoStartTime = videoStartTime;
        this.videoEndTime = videoEndTime;
        this.views = views;
        this.videoThumbnailUrl = videoThumbnailUrl;
    }

    public void addView(){
        this.views += 1;
    }
}
