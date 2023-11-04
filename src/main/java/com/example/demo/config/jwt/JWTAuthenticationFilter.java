package com.example.demo.config.jwt;

import com.example.demo.user.User;
import com.example.demo.config.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    private final String Authorization = "Authorization";

    private final JWTTokenProvider jwtTokenProvider;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTTokenProvider jwtTokenProvider) {
        super(authenticationManager);
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwtAccessToken = request.getHeader(Authorization);

        if (jwtAccessToken == null || !(jwtAccessToken.startsWith("Bearer "))) {
            chain.doFilter(request, response);
            return;
        }

        String extractedJwtAccessToken = jwtAccessToken.replace(JWTTokenProvider.Token_Prefix, "");
        if (jwtTokenProvider.validateToken(extractedJwtAccessToken)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(extractedJwtAccessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
