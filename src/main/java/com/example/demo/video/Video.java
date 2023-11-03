package com.example.demo.video;

import com.example.demo.config.utils.BaseTime;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@SQLDelete(sql = "UPDATE mentor_posts SET deleted_at = CURRENT_TIMESTAMP, is_deleted = TRUE where id = ?")
@Table(name = "videos")
public class Video extends BaseTime {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Column
    private String videoUrl;

    @Column
    private String videoInfoKorean;

    @Column
    private String videoInfoEng;

    @Column
    private String videoStartTime;

    @Column
    private String videoEndTime;

    @Builder
    public Video(String videoUrl, String videoInfoKorean, String videoInfoEng, String videoStartTime, String videoEndTime) {
        this.videoUrl = videoUrl;
        this.videoInfoKorean = videoInfoKorean;
        this.videoInfoEng = videoInfoEng;
        this.videoStartTime = videoStartTime;
        this.videoEndTime = videoEndTime;
    }
}
