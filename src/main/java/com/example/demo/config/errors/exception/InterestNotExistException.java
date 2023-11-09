package com.example.demo.config.errors.exception;

import com.example.demo.config.errors.ErrorCode;
import lombok.Getter;

@Getter
public class InterestNotExistException extends RuntimeException {
    public final ErrorCode errorCode;

    public InterestNotExistException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
