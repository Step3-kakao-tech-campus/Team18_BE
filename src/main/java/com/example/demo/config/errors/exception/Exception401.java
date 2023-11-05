package com.example.demo.config.errors.exception;

import com.example.demo.config.utils.ApiResponseBuilder;
import lombok.Getter;

@Getter
public class Exception401 extends RuntimeException {
    public Exception401(String message) {
        super(message);
    }

    public ApiResponseBuilder.ApiResponse<?> body(){
        return ApiResponseBuilder.error(getMessage());
    }
}
