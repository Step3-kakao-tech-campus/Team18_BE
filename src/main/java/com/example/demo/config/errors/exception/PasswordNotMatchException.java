package com.example.demo.config.errors.exception;

import com.example.demo.config.errors.ErrorCode;
import lombok.Getter;

@Getter
public class PasswordNotMatchException extends RuntimeException {
    public final ErrorCode errorCode;

    public PasswordNotMatchException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
