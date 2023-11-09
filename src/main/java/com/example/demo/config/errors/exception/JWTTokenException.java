package com.example.demo.config.errors.exception;

import com.example.demo.config.errors.ErrorCode;
import lombok.Getter;

@Getter
public class JWTTokenException extends RuntimeException {
    public final ErrorCode errorCode;

    public JWTTokenException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
