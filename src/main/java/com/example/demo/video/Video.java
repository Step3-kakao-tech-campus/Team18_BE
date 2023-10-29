package com.example.demo.video;

import com.example.demo.config.utils.BaseTime;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "videos")
public class Video extends BaseTime {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Column
    private String videoUrl;

    @Column
    private String videoInfo;

    @Column
    private String videoStartTime;

    @Column
    private String videoEndTime;

    @Builder
    public Video(String videoUrl, String videoInfo, String videoStartTime, String videoEndTime) {
        this.videoUrl = videoUrl;
        this.videoInfo = videoInfo;
        this.videoStartTime = videoStartTime;
        this.videoEndTime = videoEndTime;
    }
}
