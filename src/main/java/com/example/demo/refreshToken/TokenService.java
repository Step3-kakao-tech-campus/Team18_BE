package com.example.demo.refreshToken;

import com.example.demo.config.errors.exception.Exception400;
import com.example.demo.config.errors.exception.Exception401;
import com.example.demo.config.errors.exception.Exception404;
import com.example.demo.config.jwt.JWTTokenProvider;
import com.example.demo.user.User;
import com.example.demo.user.UserJPARepository;
import com.example.demo.user.userInterest.UserInterestJPARepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class TokenService {

    private final UserJPARepository userJPARepository;
    private final UserInterestJPARepository userInterestJPARepository;
    private final RefreshTokenJPARepository refreshTokenJPARepository;
    private final JWTTokenProvider jwtTokenProvider;

    public String reissueAccessToken(String jwtRefreshToken) throws UnsupportedEncodingException {

        String decodedJwtRefreshToken = URLDecoder.decode(jwtRefreshToken, "utf-8");

        if (decodedJwtRefreshToken == null || !(decodedJwtRefreshToken.startsWith("Bearer "))) {
            throw new Exception401("로그인이 필요합니다.");
        }

        String extractedJwtRefreshToken = decodedJwtRefreshToken.replace(JWTTokenProvider.Token_Prefix, "");
        try {
            if (jwtTokenProvider.validateToken(extractedJwtRefreshToken)) {
                int id = Integer.valueOf(jwtTokenProvider.decodeJwtToken(extractedJwtRefreshToken).get("id").toString());
                RefreshToken refreshTokenInfo = refreshTokenJPARepository.findByUserId(id)
                        .orElseThrow(() -> new Exception401("유효하지 않은 JWT 토큰입니다."));

                if (!extractedJwtRefreshToken.equals(refreshTokenInfo.getRefreshToken())) {
                    throw new Exception401("유효하지 않은 JWT 토큰입니다.");
                }

                User user = userJPARepository.findById(refreshTokenInfo.getUser().getId())
                        .orElseThrow(() -> new Exception404("해당 사용자가 존재하지 않습니다."));
                List<String> userCategoryList = userInterestJPARepository.findAllById(user.getId()).stream()
                        .map(interest -> interest.getInterest().getCategory())
                        .collect(Collectors.toList());

                String accessToken = JWTTokenProvider.createAccessToken(user, userCategoryList);
                return JWTTokenProvider.Token_Prefix + accessToken;
            }
        } catch (SignatureException e) {
            throw new Exception401("유효하지 않은 JWT 토큰 서명입니다.");
        } catch (MalformedJwtException e) {
            throw new Exception401("손상된 JWT 토큰입니다..");
        } catch (ExpiredJwtException e) {
            throw new Exception401("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            throw new Exception401("지원하지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            throw new Exception401("JWT 토큰 내에 정보가 없습니다.");
        }
        return null;
    }
}
