package com.example.demo.refreshToken;

import com.example.demo.config.jwt.JWTTokenProvider;
import com.example.demo.config.utils.ApiResponseBuilder;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import java.io.UnsupportedEncodingException;

@Api(tags = "Token API")
@RequiredArgsConstructor
@RestController
public class TokenController {

    private final TokenService tokenService;

    @Operation(summary = "Access Token 재발급", description = "Refresh Token을 이용하여 Access Token 재발급")
    @GetMapping(value = "/users/refresh")
    public ResponseEntity<?> refreshToken(@CookieValue(value = "RefreshToken") String refreshToken) throws UnsupportedEncodingException {
        String accessToken = tokenService.reissueAccessToken(refreshToken);
        return ResponseEntity.status(HttpStatus.OK).header(JWTTokenProvider.Header, accessToken).body(ApiResponseBuilder.successWithNoContent());
    }
}
