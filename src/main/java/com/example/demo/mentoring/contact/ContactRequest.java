package com.example.demo.mentoring.contact;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ContactRequest {

    @Getter @Setter
    public static class AcceptDTO {
        private int mentorPostId;
        private List<MentorAndMenteeDTO> mentorsAndMentees;
        @Getter @Setter
        public static class MentorAndMenteeDTO {
            @NotNull
            private int mentorId;
            @NotNull
            private int menteeId;

            public MentorAndMenteeDTO(int mentorId, int menteeId) {
                this.mentorId = mentorId;
                this.menteeId = menteeId;
            }
        }
    }
}
