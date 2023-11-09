package com.example.demo.config.errors.exception;

import com.example.demo.config.errors.ErrorCode;
import lombok.Getter;

@Getter
public class DuplicateEmailException extends RuntimeException {
    public final ErrorCode errorCode;

    public DuplicateEmailException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
