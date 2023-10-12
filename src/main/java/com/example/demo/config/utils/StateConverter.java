package com.example.demo.config.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class StateConverter implements AttributeConverter<StateEnum, String> {

    @Override
    public String convertToDatabaseColumn(StateEnum attribute) {
        return Optional.ofNullable(attribute).orElse(StateEnum.NULL).getValue();
    }

    @Override
    public StateEnum convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.length() == 0) {
            return StateEnum.NULL;
        }
        return StateEnum.findOf(dbData);
    }
}
