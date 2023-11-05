package com.example.demo.refreshToken;

import com.example.demo.config.errors.exception.Exception400;
import com.example.demo.config.errors.exception.Exception401;
import com.example.demo.config.jwt.JWTTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class TokenService {

    private final RefreshTokenJPARepository refreshTokenJPARepository;
    private final JWTTokenProvider jwtTokenProvider;

    public String reissueAccessToken(String jwtRefreshToken) {
        if (jwtRefreshToken == null || !(jwtRefreshToken.startsWith("Bearer "))) {
            throw new Exception401("유효하지 않은 토큰입니다.1");
        }

        String extractedJwtRefreshToken = jwtRefreshToken.replace(JWTTokenProvider.Token_Prefix, "");
        if (jwtTokenProvider.validateToken(extractedJwtRefreshToken)) {
            int id = Integer.valueOf(jwtTokenProvider.decodeJwtToken(extractedJwtRefreshToken).get("id").toString());
            RefreshToken refreshTokenInfo = refreshTokenJPARepository.findByUserId(id)
                    .orElseThrow(() -> new Exception401("유효하지 않은 토큰입니다.2"));

            if (!extractedJwtRefreshToken.equals(refreshTokenInfo.getRefreshToken())) {
                throw new Exception401("유효하지 않은 토큰입니다.3");
            }

            String accessToken = JWTTokenProvider.createAccessToken(refreshTokenInfo.getUser());
            return JWTTokenProvider.Token_Prefix + accessToken;
        }
        return null;
    }
}
