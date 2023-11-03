package com.example.demo.mentoring.contact;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;

@RequiredArgsConstructor
@Component
public class ContactStateConverter implements AttributeConverter<ContactStateEnum, String> {
    @Override
    public String convertToDatabaseColumn(ContactStateEnum attribute) {
        return attribute.getValue();
    }

    @Override
    public ContactStateEnum convertToEntityAttribute(String dbData) {
        return ContactStateEnum.findOf(dbData);
    }
}
