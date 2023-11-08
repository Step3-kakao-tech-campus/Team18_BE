package com.example.demo.config.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

public class ApiResponseBuilder {

    private static final String SUCCESS_STATUS = "success";
    private static final String FAIL_STATUS = "fail";
    private static final String ERROR_STATUS = "error";

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ApiResponse<T> {
        @Schema(description = "상태", nullable = false)
        private final String status;
        @Schema(description = "성공 시, 응답 데이터", nullable = false)
        private final T data;
        @Schema(description = "실패 시, 실패 메시지", nullable = false)
        private final T message;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(SUCCESS_STATUS, data, null);
    }

    public static <T> ApiResponse<T> successWithNoContent() {
        return new ApiResponse<>(SUCCESS_STATUS, null, null);
    }

    public static ApiResponse<?> fail(Map<String, String> errors) {
        return new ApiResponse<>(FAIL_STATUS, null, errors);
    }

    public static ApiResponse<?> error(String message) {
        return new ApiResponse<>(ERROR_STATUS, null, message);
    }
}
