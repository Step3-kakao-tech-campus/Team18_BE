package com.example.demo.video;

import com.example.demo.config.utils.BaseTime;
import com.example.demo.interest.Interest;
import com.example.demo.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "videoInterests")
public class VideoInterest extends BaseTime {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id")
    private Video video;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interest_id")
    private Interest interest;

    @Builder
    public VideoInterest(Video video, Interest interest) {
        this.video = video;
        this.interest = interest;
    }
}
