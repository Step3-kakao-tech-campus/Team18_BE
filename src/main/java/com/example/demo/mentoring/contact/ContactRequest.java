package com.example.demo.mentoring.contact;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ContactRequest {

    @Getter @Setter
    public static class ContactCreateDTO {
        private int mentorPostId;
        private int mentorId;
        private int menteeId;
    }

    @Getter @Setter
    public static class ContactRefuseDTO {
        private int mentorPostId;
        private int mentorId;
        private List<MenteeDTO> mentees;
        @Getter @Setter @NoArgsConstructor
        public static class MenteeDTO {
            @NotNull
            private int menteeId;
            public MenteeDTO(int menteeId) {
                this.menteeId = menteeId;
            }
        }
    }

    @Getter @Setter
    public static class ContactAcceptDTO {
        private int mentorPostId;
        private int mentorId;
        private List<MenteeDTO> mentees;
        @Getter @Setter @NoArgsConstructor
        public static class MenteeDTO {
            @NotNull
            private int menteeId;
            public MenteeDTO(int menteeId) {
                this.menteeId = menteeId;
            }
        }
    }
}
