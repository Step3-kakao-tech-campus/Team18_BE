package com.example.demo.video;

import com.example.demo.interest.Interest;
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
    public static class CreateDTO {
        private String videoUrl;
        private String videoTitleKorean;
        private String videoTitleEng;
        private String videoStartTime;
        private String videoEndTime;
        private String videoThumbnailUrl;
        private Interest videoInterest;
        private List<SubtitleCreateDTO> subtitleCreateDTOList;

        @Getter
        @Setter
        public static class SubtitleCreateDTO{
            private String korSubtitleContent;
            private String engSubtitleContent;
            private String korStartTime;
            private String engStartTime;
            private String korEndTime;
            private String engEndTime;
        }
    }
}
