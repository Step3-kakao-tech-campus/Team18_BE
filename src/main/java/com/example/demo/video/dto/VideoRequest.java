package com.example.demo.video.dto;

import com.example.demo.interest.Interest;
import com.example.demo.video.domain.VideoSubtitle;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.List;

public class VideoRequest {
    @Getter
    @Setter
    public static class VideoDTO {
        @NotNull
        private String videoUrl;

        @NotNull
        private String videoTitleKorean;

        @NotNull
        private String videoTitleEng;

        @NotNull
        private String VideoIntroduction;

        @NotNull
        private String videoStartTime;

        @NotNull
        private String videoEndTime;

        @NotNull
        private String videoThumbnailUrl;

        @NotNull
        private List<String> categoryList;

        @NotNull
        private List<VideoSubtitleDTO> videoSubtitleDTOList;

        @Getter
        @Setter
        public static class VideoSubtitleDTO {
            private String korSubtitleContent;
            private String engSubtitleContent;
            private String korStartTime;
            private String engStartTime;
            private String korEndTime;
            private String engEndTime;
        }
    }
}
