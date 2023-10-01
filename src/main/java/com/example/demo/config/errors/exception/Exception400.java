package com.example.demo.config.errors.exception;

import com.example.demo.config.utils.ApiUtils;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class Exception400 extends RuntimeException {
    public Exception400(String message) {
        super(message);
    }

    public ApiUtils.ApiResult<?> body(){
        return ApiUtils.error(getMessage(), HttpStatus.BAD_REQUEST);
    }

    public HttpStatus status(){
        return HttpStatus.BAD_REQUEST;
    }
}

