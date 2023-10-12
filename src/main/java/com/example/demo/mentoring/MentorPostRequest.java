package com.example.demo.mentoring;

import com.example.demo.user.User;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;

public class MentorPostRequest {

    @Getter
    @Setter
    public static class CreateDTO {
        @NotNull
        private String title;

        private String content;

        public MentorPost toEntity(User writer) {
            return MentorPost.builder()
                    .writer(writer)
                    .title(title)
                    .content(content)
                    .build();
        }
    }
}
