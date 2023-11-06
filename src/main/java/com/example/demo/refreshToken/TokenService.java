package com.example.demo.refreshToken;

import com.example.demo.config.errors.exception.Exception400;
import com.example.demo.config.errors.exception.Exception401;
import com.example.demo.config.errors.exception.Exception404;
import com.example.demo.config.jwt.JWTTokenProvider;
import com.example.demo.user.User;
import com.example.demo.user.UserJPARepository;
import com.example.demo.user.userInterest.UserInterestJPARepository;
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
            throw new Exception401("유효하지 않은 토큰입니다.1");
        }


        String extractedJwtRefreshToken = decodedJwtRefreshToken.replace(JWTTokenProvider.Token_Prefix, "");
        if (jwtTokenProvider.validateToken(extractedJwtRefreshToken)) {
            int id = Integer.valueOf(jwtTokenProvider.decodeJwtToken(extractedJwtRefreshToken).get("id").toString());
            RefreshToken refreshTokenInfo = refreshTokenJPARepository.findByUserId(id)
                    .orElseThrow(() -> new Exception401("유효하지 않은 토큰입니다.2"));

            if (!extractedJwtRefreshToken.equals(refreshTokenInfo.getRefreshToken())) {
                throw new Exception401("유효하지 않은 토큰입니다.3");
            }

            User user = userJPARepository.findById(refreshTokenInfo.getUser().getId())
                    .orElseThrow(() -> new Exception404("해당 사용자가 존재하지 않습니다."));
            List<String> userCategoryList = userInterestJPARepository.findAllById(user.getId()).stream()
                    .map(interest -> interest.getInterest().getCategory())
                    .collect(Collectors.toList());

            String accessToken = JWTTokenProvider.createAccessToken(user, userCategoryList);
            return JWTTokenProvider.Token_Prefix + accessToken;
        }
        return null;
    }
}
