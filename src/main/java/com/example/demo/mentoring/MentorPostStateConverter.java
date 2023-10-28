package com.example.demo.mentoring;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class MentorPostStateConverter implements AttributeConverter<MentorPostStateEnum, String> {

    @Override
    public String convertToDatabaseColumn(MentorPostStateEnum attribute) {
        return attribute.getValue();
    }

    @Override
    public MentorPostStateEnum convertToEntityAttribute(String dbData) {
        return MentorPostStateEnum.findOf(dbData);
    }
}
