package com.example.demo.mentoring;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MentorPostRequest {

    @Getter
    @Setter
    public static class CreateMentorPostDTO {
        @NotNull
        @Size(max = 50, message = "50자를 초과하면 안됩니다.")
        private String title;

        @Size(max = 300, message = "300자를 초과하면 안됩니다.")
        private String content;
    }

    @Getter
    @Setter
    public static class StateDTO {
        private MentorPostStateEnum mentorPostStateEnum;
    }
}
