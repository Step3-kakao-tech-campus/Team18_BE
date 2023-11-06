package com.example.demo.config.jwt;

import com.example.demo.config.auth.CustomUserDetailService;
import com.example.demo.config.auth.CustomUserDetails;
import com.example.demo.config.errors.exception.Exception401;
import com.example.demo.user.User;
import com.example.demo.refreshToken.RefreshToken;
import com.example.demo.refreshToken.RefreshTokenJPARepository;
import com.example.demo.refreshToken.TokenResponse;
import com.example.demo.user.userInterest.UserInterest;
import com.example.demo.user.userInterest.UserInterestJPARepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class JWTTokenProvider {

    public static final String Header = "Authorization";
    public static final String Token_Prefix = "Bearer ";

    public static final String SecretKey = "a2FrYW8tdGVjaC1jYW1wdXMtcHJvamVjdC1nYXJkZW4tc3ByaW5nLXNlY3VyaXR5LWp3dC10b2tlbi1zZWNyZXQta2V5";
    public static final int AccessTokenValidTime = 1000 * 60 * 5; // 5분
    public static final int RefreshTokenValidTime = 1000 * 60 * 60 * 24 * 7; // 1주일

    private final CustomUserDetailService userDetailService;

    public static TokenResponse.TokenDTO createToken(User user, List<String> userCategoryList) {
        String accessToken = createAccessToken(user, userCategoryList);
        String refreshToken = createRefreshToken(user);
        return new TokenResponse.TokenDTO(accessToken, refreshToken);
    }

    public static String createAccessToken(User user, List<String> userCategoryList) {
        String accessToken = Jwts.builder()
                .setSubject(user.getEmail())
                .claim("id", user.getId())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .claim("email", user.getEmail())
                .claim("country", user.getCountry())
                .claim("introduction", user.getIntroduction())
                .claim("birthDate", user.getBirthDate().toString())
                .claim("phone", user.getPhone())
                .claim("profileImage", user.getProfileImage())
                .claim("role", user.getRole())
                .claim("categoryList", userCategoryList)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + AccessTokenValidTime))
                .signWith(SignatureAlgorithm.HS512, SecretKey)
                .compact();
        return accessToken;
    }

    public static String createRefreshToken(User user) {
        String refreshToken = Jwts.builder()
                .setSubject(user.getEmail())
                .claim("id", user.getId())
                .claim("email", user.getEmail())
                .claim("role", user.getRole())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + RefreshTokenValidTime))
                .signWith(SignatureAlgorithm.HS512, SecretKey)
                .compact();
        return refreshToken;
    }

    public Claims decodeJwtToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SecretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

    // JWT 토큰 복호화 -> 인증 정보 조회
    public Authentication getAuthentication(String accessToken) {
        Claims claims = decodeJwtToken(accessToken);
        CustomUserDetails userDetails = userDetailService.loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SecretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            System.out.println("Invalid JWT Signature.");
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT Token.");
        } catch (ExpiredJwtException e) {
            System.out.println("Expired JWT Token.");
        } catch (UnsupportedJwtException e) {
            System.out.println("Unsupported JWT Token.");
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty.");
        }
        return false;
    }
}
