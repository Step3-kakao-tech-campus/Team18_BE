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
        private int connectionId;

        public ContactRefuseDTO ( int connectionId ) {
            this.connectionId = connectionId;
        }

    }

    @Getter @Setter
    public static class ContactAcceptDTO {
        private int connectionId;

        public ContactAcceptDTO ( int connectionId ) {
            this.connectionId = connectionId;
        }
    }
}
