package com.example.demo.config.errors.exception;

import com.example.demo.config.errors.ErrorCode;
import lombok.Getter;

@Getter
public class EmailNotMatchException extends RuntimeException {
    public final ErrorCode errorCode;

    public EmailNotMatchException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
