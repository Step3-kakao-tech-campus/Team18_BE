package com.example.demo.video;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class VideoResponse {
    @Getter
    @Setter
    public static class VideoAllResponseDTO {
        private int videoID;
        private String videoUrl;
        private String videoTitleKorean;
        private String videoTitleEng;
        private List<String> interests;
        private long views;
        private String videoThumbnailUrl;
        private String videoStartTime;
        private String videoEndTime;

        public VideoAllResponseDTO(Video video, List<VideoInterest> videoInterests)
        {
            this.videoID = video.getId();
            this.videoUrl = video.getVideoUrl();
            this.videoTitleKorean = video.getVideoTitleKorean();
            this.videoTitleEng = video.getVideoTitleEng();
            this.interests = videoInterests.stream()
                .map(videoInterest -> videoInterest.getInterest().getCategory())
                .collect(Collectors.toList());
            this.views = video.getViews();
            this.videoThumbnailUrl = video.getVideoThumbnailUrl();
            this.videoStartTime = video.getVideoStartTime();
            this.videoEndTime = video.getVideoEndTime();
        }
    }

    @Getter
    @Setter
    public static class VideoResponseDTO {
        private int videoID;
        private String videoUrl;
        private String videoTitleKorean;
        private String videoTitleEng;
        private List<String> interests;
        private long views;
        private String videoThumbnailUrl;
        private String videoStartTime;
        private String videoEndTime;
        private Subtitle subtitle;

        public VideoResponseDTO(Video video, List<VideoInterest> videoInterests, Subtitle subtitle)
        {
            this.videoID = video.getId();
            this.videoUrl = video.getVideoUrl();
            this.videoTitleKorean = video.getVideoTitleKorean();
            this.videoTitleEng = video.getVideoTitleEng();
            this.interests = videoInterests.stream()
                    .map(videoInterest -> videoInterest.getInterest().getCategory())
                    .collect(Collectors.toList());
            this.views = video.getViews();
            this.videoThumbnailUrl = video.getVideoThumbnailUrl();
            this.videoStartTime = video.getVideoStartTime();
            this.videoEndTime = video.getVideoEndTime();
        }
    }
}
