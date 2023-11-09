package com.example.demo.config.errors;

import com.example.demo.config.errors.exception.*;
import com.example.demo.config.utils.ApiResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> validationException(ValidationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseBuilder.fail(e.getErrorList()));
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<?> duplicatedEmailException(DuplicateEmailException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseBuilder.error(e.getErrorCode()));
    }

    @ExceptionHandler(EmailNotMatchException.class)
    public ResponseEntity<?> emailNotMatchException(EmailNotMatchException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseBuilder.error(e.getErrorCode()));
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<?> passwordNotMatchException(PasswordNotMatchException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseBuilder.error(e.getErrorCode()));
    }

    @ExceptionHandler(InterestNotExistException.class)
    public ResponseEntity<?> interestNotExistException(InterestNotExistException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponseBuilder.error(e.getErrorCode()));
    }

    @ExceptionHandler(UserNotExistException.class)
    public ResponseEntity<?> userNotExistException(UserNotExistException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponseBuilder.error(e.getErrorCode()));
    }

    @ExceptionHandler(JWTTokenException.class)
    public ResponseEntity<?> jwtTokenException(JWTTokenException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseBuilder.error(e.getErrorCode()));
    }

}
