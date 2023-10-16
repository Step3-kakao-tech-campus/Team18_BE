package com.example.demo.mentoring.contact;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

public class ContactRequest {

    @Getter @Setter
    public static class AcceptDTO {
        @NotNull
        private int mentorId;
        @NotNull
        private int menteeId;
    }
}
