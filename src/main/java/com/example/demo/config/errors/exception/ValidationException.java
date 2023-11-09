package com.example.demo.config.errors.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class ValidationException extends RuntimeException{
    public final Map<String, String> errorList;

    public ValidationException(Map<String, String> errorList) {
        this.errorList = errorList;
    }
}
