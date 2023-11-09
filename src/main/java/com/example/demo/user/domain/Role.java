package com.example.demo.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum Role {
    ADMIN("ADMIN", "ADMIN"),
    MENTOR("MENTOR", "MENTOR"),
    MENTEE("MENTEE", "MENTEE");

    private final String desc;
    private final String value;

    private static final Map<String, Role> description = Collections.unmodifiableMap(Stream.of(values()).collect(Collectors.toMap(Role::getValue, Function.identity())));

    public static Role findOf(String findValue) {
        return description.get(findValue);
    }
}
