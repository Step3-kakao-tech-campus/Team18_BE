package com.example.demo.config.errors;

import com.example.demo.config.errors.exception.Exception400;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Aspect
@Component
public class GlobalValidationHandler {
    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postMapping() {}

    @Before("postMapping()")
    public void validationAdvice(JoinPoint joinPoint) {
        Map<String, String> errorList = new HashMap<>();

        Object[] args = joinPoint.getArgs(); // join point parameter

        for (Object arg : args) {
            if (arg instanceof Errors) {
                Errors errors = (Errors) arg;

                if (errors.hasErrors()) {
                    List<FieldError> fieldErrors = errors.getFieldErrors();
                    for (FieldError fieldError : fieldErrors) {
                        errorList.put(fieldError.getField(), fieldError.getDefaultMessage());
                    }
                }
            }
        }

        if (!errorList.isEmpty()) {
            throw new Exception400(errorList, null);
        }
    }
}
