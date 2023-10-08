package com.example.demo.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public enum Role {
    ADMIN("Admin"),
    MENTOR("Mentor"),
    MENTEE("Mentee");

    private final String role;

    @JsonCreator
    public static Role parsing(String value) {
        for (Role role: Role.values()) {
            if (role.getRole().equals(value)) {
                return role;
            }
        }
        return null;
    }
}
