package com.example.demo.mentoring;

import com.example.demo.account.Account;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;

public class MentorPostRequest {

    @Getter
    @Setter
    public class CreateDTO {
        @NotNull
        private String title;

        private String content;

        public MentorPost toEntity(Account writer) {
            return MentorPost.builder()
                    .writer(writer)
                    .title(title)
                    .content(content)
                    .build();
        }
    }
}
