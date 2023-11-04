package com.example.demo.config.errors.exception;

import com.example.demo.config.utils.ApiResponseBuilder;

public class Exception404 extends RuntimeException {
    public Exception404(String message) {
        super(message);
    }

    public ApiResponseBuilder.ApiResponse<?> body(){
        return ApiResponseBuilder.error(getMessage());
    }
}
