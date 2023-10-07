package com.example.demo.config.errors.exception;

import com.example.demo.config.utils.ApiUtils;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class Exception403 extends RuntimeException {
    public Exception403(String message) {
        super(message);
    }

    public ApiUtils.ApiResponse<?> body(){
        return ApiUtils.error(getMessage());
    }
}