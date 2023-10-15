package com.example.demo.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.user.User;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTokenProvider {
    public static final String SECRET = "SecretKey";
    public static final Long EXP = 1000L * 60 * 30; // 30분
    public static final String HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer "; // 스페이스 필요함

    public static String create(User account) {
        String jwt = JWT.create()
                .withSubject(account.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXP))
                .withClaim("user_id", account.getId())
                .withClaim("user_email", account.getEmail())
                .sign(Algorithm.HMAC512(SECRET));
        return TOKEN_PREFIX + jwt;
    }

    public static DecodedJWT verify(String jwt) throws SignatureVerificationException, TokenExpiredException {
        jwt = jwt.replace(JWTTokenProvider.TOKEN_PREFIX, "");
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SECRET))
                .build().verify(jwt);
        return decodedJWT;
    }
}
