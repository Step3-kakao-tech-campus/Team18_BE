package com.example.demo.video;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class VideoResponse {
    @Getter
    @Setter
    public static class VideoResponseDTO {
        private int videoID;
        private String videoUrl;
        private String videoInfoKorean;
        private String videoInfoEng;
        private List<String> interests;
        private List<Subtitle> subtitle;
        private long views;
        private String videoThumbnailUrl;

        public VideoResponseDTO(Video video, List<VideoInterest> videoInterests, List<Subtitle> subtitle)
        {
            this.videoID = video.getId();
            this.videoUrl = video.getVideoUrl();
            this.videoInfoKorean = video.getVideoTitleKorean();
            this.videoInfoEng = video.getVideoTitleEng();
            this.interests = videoInterests.stream()
                .map(videoInterest -> videoInterest.getInterest().getCategory())
                .collect(Collectors.toList());
            this.subtitle = subtitle;
            this.views = video.getViews();
            this.videoThumbnailUrl = video.getVideoThumbnailUrl();
        }
    }
}
