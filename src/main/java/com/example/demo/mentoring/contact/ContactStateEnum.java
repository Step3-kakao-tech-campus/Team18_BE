package com.example.demo.mentoring.contact;

import com.example.demo.mentoring.MentorPostStateEnum;
//import io.swagger.models.Contact;
import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum ContactStateEnum {
    ACCEPT("ACCEPT", "ACCEPT"),
    REFUSE("REFUSE", "REFUSE"),
    AWAIT("AWAIT", "AWAIT");

    private final String desc;
    private final String value;

    ContactStateEnum(String desc, String value) {
        this.desc = desc;
        this.value = value;
    }

    private static final Map<String, ContactStateEnum> descriptions = Collections.unmodifiableMap(Stream.of(values()).collect(Collectors.toMap(ContactStateEnum::getValue, Function.identity())));

    public static ContactStateEnum findOf(String findValue) {
        return descriptions.get(findValue);
    }
}
