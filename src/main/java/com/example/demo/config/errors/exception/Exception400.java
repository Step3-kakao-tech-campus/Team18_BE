package com.example.demo.config.errors.exception;

import com.example.demo.config.utils.ApiUtils;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Exception400 extends RuntimeException {
    Map<String, String> errors;

    public Exception400(Map<String, String> errors, String message) {
        super(message);
        this.errors = errors;
    }

    public Exception400(String message) {
        super(message);
        errors = null;
    }

    public ApiUtils.ApiResponse<?> body(){
        return ApiUtils.fail(errors, getMessage());
    }
}

