package com.example.demo.config.utils;

import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum StateEnum {
    NULL("NULL", ""),
    ACTIVE("ACTIVE", "ACTIVE"),
    DONE("DONE", "DONE"),
    DELETED("DELETED", "DELETED");

    private final String desc;
    private final String value;

    StateEnum(String desc, String value) {
        this.desc = desc;
        this.value = value;
    }

    private static final Map<String, StateEnum> descriptions = Collections.unmodifiableMap(Stream.of(values()).collect(Collectors.toMap(StateEnum::getValue, Function.identity())));

    public static StateEnum findOf(String findValue) {
        return Optional.ofNullable(descriptions.get(findValue)).orElse(NULL);
    }
}
