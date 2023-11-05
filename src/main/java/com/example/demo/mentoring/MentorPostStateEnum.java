package com.example.demo.mentoring;

import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum MentorPostStateEnum {
    ACTIVE("ACTIVE", "ACTIVE"),
    DONE("DONE", "DONE");

    private final String desc;
    private final String value;

    MentorPostStateEnum(String desc, String value) {
        this.desc = desc;
        this.value = value;
    }

    private static final Map<String, MentorPostStateEnum> descriptions = Collections.unmodifiableMap(Stream.of(values()).collect(Collectors.toMap(MentorPostStateEnum::getValue, Function.identity())));

    public static MentorPostStateEnum findOf(String findValue) {
        return descriptions.get(findValue);
    }
}
