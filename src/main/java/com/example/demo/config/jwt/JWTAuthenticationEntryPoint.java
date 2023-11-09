package com.example.demo.config.jwt;

import com.example.demo.config.errors.exception.Exception401;
import com.example.demo.config.utils.ApiResponseBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.h2.util.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String exception = (String) request.getAttribute("exception");

        if (exception == null) {
            setResponse(response, "로그인이 필요합니다.");
        } else if (exception.equals("유효하지 않은 JWT 토큰 서명입니다.")) {
            setResponse(response, "유효하지 않은 JWT 토큰 서명입니다.");
        } else if (exception.equals("손상된 JWT 토큰입니다.")) {
            setResponse(response, "손상된 JWT 토큰입니다.");
        } else if (exception.equals("만료된 JWT 토큰입니다.")) {
            setResponse(response, "만료된 JWT 토큰입니다.");
        } else if (exception.equals("지원하지 않는 JWT 토큰입니다.")) {
            setResponse(response, "지원하지 않는 JWT 토큰입니다.");
        }
    }

    private void setResponse(HttpServletResponse response, String message) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ObjectMapper om = new ObjectMapper();
        String responseBody = om.writeValueAsString(new Exception401(message).body());
        response.getWriter().println(responseBody);
    }
}
