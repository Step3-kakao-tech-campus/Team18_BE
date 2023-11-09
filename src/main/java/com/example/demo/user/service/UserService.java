package com.example.demo.user.service;

import com.example.demo.config.security.CustomUserDetails;
import com.example.demo.config.errors.ErrorCode;
import com.example.demo.config.errors.exception.*;
import com.example.demo.config.s3.S3Uploader;
import com.example.demo.interest.Interest;
import com.example.demo.interest.InterestJPARepository;
import com.example.demo.config.security.JWTTokenProvider;
import com.example.demo.user.domain.RefreshToken;
import com.example.demo.user.dto.TokenResponse;
import com.example.demo.user.repository.RefreshTokenJPARepository;
import com.example.demo.user.repository.UserJPARepository;
import com.example.demo.user.dto.UserRequest;
import com.example.demo.user.dto.UserResponse;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserInterest;
import com.example.demo.user.repository.UserInterestJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

    private final S3Uploader s3Uploader;
    private final PasswordEncoder passwordEncoder;
    private final UserJPARepository userJPARepository;
    private final RefreshTokenJPARepository refreshTokenJPARepository;
    private final InterestJPARepository interestJPARepository;
    private final UserInterestJPARepository userInterestJPARepository;
    private final JWTTokenProvider jwtTokenProvider;

    public void emailCheck(UserRequest.EmailCheckDTO requestDTO) {
        Optional<User> optionalUser = userJPARepository.findByEmail(requestDTO.getEmail());
        if (optionalUser.isPresent()) {
            throw new DuplicateEmailException(ErrorCode.DUPLICATE_EMAIL);
        }
    }

    @Transactional
    public void signup(UserRequest.SignUpDTO requestDTO, MultipartFile file) throws IOException {
        String profileImageURL = s3Uploader.uploadFile(file);
        System.out.println(profileImageURL);

        requestDTO.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        User user = userJPARepository.save(requestDTO.toEntity(profileImageURL));

        List<UserInterest> userInterestList = new ArrayList<>();
        List<String> categoryList = requestDTO.getCategoryList();
        for (String category : categoryList) {
            Interest interest = interestJPARepository.findByCategory(category)
                    .orElseThrow(() -> new InterestNotExistException(ErrorCode.NOT_EXIST_INTEREST));
            UserInterest userInterest = UserInterest.builder().user(user).interest(interest).build();
            userInterestList.add(userInterest);
        }
        userInterestJPARepository.saveAll(userInterestList);
    }

    @Transactional
    public UserResponse.LoginDTO login(UserRequest.LoginDTO requestDTO) {
        User user = userJPARepository.findByEmail(requestDTO.getEmail())
                .orElseThrow(() -> new EmailNotMatchException(ErrorCode.NOT_MATCH_EMAIL));

        if (!passwordEncoder.matches(requestDTO.getPassword(), user.getPassword())) {
            throw new PasswordNotMatchException(ErrorCode.NOT_MATCH_PASSWORD);
        }

        List<String> userCategoryList = userInterestJPARepository.findAllById(user.getId()).stream()
                .map(interest -> interest.getInterest().getCategory())
                .collect(Collectors.toList());

        TokenResponse.TokenDTO token = JWTTokenProvider.createToken(user, userCategoryList);

        Optional<RefreshToken> refreshTokenInfo = refreshTokenJPARepository.findByUser(user);
        if (refreshTokenInfo.isPresent()) {
            refreshTokenInfo.get().updateRefreshToken(token.getRefreshToken());
        } else {
            RefreshToken newRefreshToken = RefreshToken.builder().user(user).refreshToken(token.getRefreshToken()).build();
            refreshTokenJPARepository.save(newRefreshToken);
        }

        return new UserResponse.LoginDTO(user, userCategoryList, token);
    }

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

    public UserResponse.SimpleProfileDTO findSimpleProfile(CustomUserDetails userDetails) {
        User user = userJPARepository.findById(userDetails.getUser().getId())
                .orElseThrow(() -> new UserNotExistException(ErrorCode.NOT_EXIST_USER));
        return new UserResponse.SimpleProfileDTO(user);
    }

    public UserResponse.ProfileDTO findProfile(Integer id, CustomUserDetails userDetails) {
        User user;
        List<String> userCategoryList = new ArrayList<>();

        if (id == null) {
            user = userJPARepository.findById(userDetails.getUser().getId())
                    .orElseThrow(() ->  new UserNotExistException(ErrorCode.NOT_EXIST_USER));
        } else {
            user = userJPARepository.findById(id)
                    .orElseThrow(() ->  new UserNotExistException(ErrorCode.NOT_EXIST_USER));
            userCategoryList = userInterestJPARepository.findAllById(user.getId()).stream()
                    .map(interest -> interest.getInterest().getCategory())
                    .collect(Collectors.toList());
        }
        return new UserResponse.ProfileDTO(user, userCategoryList);
    }

    public void passwordCheck(UserRequest.PasswordCheckDTO requestDTO, CustomUserDetails userDetails) {
        User user = userJPARepository.findById(userDetails.getUser().getId())
                .orElseThrow(() ->  new UserNotExistException(ErrorCode.NOT_EXIST_USER));

        if (!passwordEncoder.matches(requestDTO.getPassword(), user.getPassword())) {
            throw new PasswordNotMatchException(ErrorCode.NOT_MATCH_PASSWORD);
        }
    }

    @Transactional
    public UserResponse.ProfileDTO updateProfile(CustomUserDetails userDetails, UserRequest.ProfileUpdateDTO requestDTO, MultipartFile file) throws IOException {
        User user = userJPARepository.findById(userDetails.getUser().getId())
                .orElseThrow(() ->  new UserNotExistException(ErrorCode.NOT_EXIST_USER));

        String profileImageURL = user.getProfileImage();
        String key = profileImageURL.split("/")[3];
        s3Uploader.deleteFile(key);

        String newProfileImageURL = s3Uploader.uploadFile(file);
        user = user.updateProfile(requestDTO.toEntity(newProfileImageURL));

        List<String> userCategoryList = userInterestJPARepository.findAllById(user.getId()).stream()
                .map(interest -> interest.getInterest().getCategory())
                .collect(Collectors.toList());
        List<String> newUserCategoryList = requestDTO.getCategoryList();

        for (String newUserCategory : newUserCategoryList) {
            if (!userCategoryList.contains(newUserCategory)) {
                Interest interest = interestJPARepository.findByCategory(newUserCategory)
                        .orElseThrow(() -> new InterestNotExistException(ErrorCode.NOT_EXIST_INTEREST));
                UserInterest newUserInterest = UserInterest.builder().user(user).interest(interest).build();
                userInterestJPARepository.save(newUserInterest);
            }
        }

        for (String userCategory: userCategoryList) {
            if (!newUserCategoryList.contains(userCategory)) {
                 userInterestJPARepository.deleteByUserAndInterest(user.getId(), userCategory);
            }
        }

        return new UserResponse.ProfileDTO(user, userCategoryList);
    }
}