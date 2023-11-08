package com.example.demo.mentoring;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;

public class MentorPostRequest {

    @Getter
    @Setter
    public static class CreateMentorPostDTO {
        @NotNull
        private String title;

        private String content;
    }

    @Getter
    @Setter
    public static class StateDTO {
        private MentorPostStateEnum mentorPostStateEnum;
    }
}
