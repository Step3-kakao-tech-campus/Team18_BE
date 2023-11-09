package com.example.demo.config.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {
    /**
     * status: 400
     */
    DUPLICATE_EMAIL("동일한 이메일이 존재합니다."),
    NOT_MATCH_EMAIL("이메일이 일치하지 않습니다."),
    NOT_MATCH_PASSWORD("비밀번호가 일치하지 않습니다."),
    INVALID_JWT_SIGNATUE("유효하지 않은 JWT 토큰 서명입니다."),
    INVALID_JWT_TOKEN("손상된 JWT 토큰입니다."),
    EXPIRED_JWT_TOKEN("만료된 JWT 토큰입니다."),
    UNSUPPORTED_JWT_TOKEN("지원하지 않는 JWT 토큰입니다."),
    ILLEGAL_ARGUMENT_EXCEPTION("JWT 토큰 내의 정보가 없습니다"),

    /**
     * status: 404
     */
    NOT_EXIST_INTEREST("관심사가 존재하지 않습니다."),
    NOT_EXIST_USER("사용자가 존재하지 않습니다.");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }
}
