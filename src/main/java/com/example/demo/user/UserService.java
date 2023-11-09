package com.example.demo.user;

import com.example.demo.config.auth.CustomUserDetails;
import com.example.demo.config.errors.exception.Exception400;
import com.example.demo.config.errors.exception.Exception404;
import com.example.demo.config.s3.S3Uploader;
import com.example.demo.interest.Interest;
import com.example.demo.interest.InterestJPARepository;
import com.example.demo.config.jwt.JWTTokenProvider;
import com.example.demo.refreshToken.RefreshToken;
import com.example.demo.refreshToken.RefreshTokenJPARepository;
import com.example.demo.refreshToken.TokenResponse;
import com.example.demo.user.userInterest.UserInterest;
import com.example.demo.user.userInterest.UserInterestJPARepository;
import jdk.jfr.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
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

    public void emailCheck(UserRequest.EmailCheckDTO requestDTO) {
        Optional<User> optionalUser = userJPARepository.findByEmail(requestDTO.getEmail());
        if (optionalUser.isPresent()) {
            throw new Exception400(null, "동일한 이메일이 존재합니다.");
        }
    }

    @Transactional
    public void signup(UserRequest.SignUpDTO requestDTO) {
        requestDTO.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        User user = userJPARepository.save(requestDTO.toEntity());

        List<UserInterest> userInterestList = new ArrayList<>();
        List<String> categoryList = requestDTO.getCategoryList();
        for (String category : categoryList) {
            Interest interest = interestJPARepository.findByCategory(category)
                    .orElseThrow(() -> new Exception400(null, "해당 관심사가 존재하지 않습니다."));
            UserInterest userInterest = UserInterest.builder().user(user).interest(interest).build();
            userInterestList.add(userInterest);
        }
        userInterestJPARepository.saveAll(userInterestList);
    }

    @Transactional
    public UserResponse.LoginDTO login(UserRequest.LoginDTO requestDTO) {
        User user = userJPARepository.findByEmail(requestDTO.getEmail())
                .orElseThrow(() -> new Exception400(null, "잘못된 이메일입니다."));

        if (!passwordEncoder.matches(requestDTO.getPassword(), user.getPassword())) {
            throw new Exception400(null, "잘못된 비밀번호입니다.");
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

    public UserResponse.SimpleProfileDTO findSimpleProfile(CustomUserDetails userDetails) {
        User user = userJPARepository.findById(userDetails.getUser().getId())
                .orElseThrow(() -> new Exception404("해당 사용자가 존재하지 않습니다."));
        return new UserResponse.SimpleProfileDTO(user);
    }

    public UserResponse.ProfileDTO findProfile(Integer id, CustomUserDetails userDetails) {
        User user;

        if (id == null) {
            user = userJPARepository.findById(userDetails.getUser().getId())
                    .orElseThrow(() -> new Exception404("해당 사용자가 존재하지 않습니다."));
        } else {
            user = userJPARepository.findById(id)
                    .orElseThrow(() -> new Exception404("해당 사용자가 존재하지 않습니다."));
        }
        List<String> userCategoryList = userCategoryList = userInterestJPARepository.findAllById(user.getId()).stream()
                .map(interest -> interest.getInterest().getCategory())
                .collect(Collectors.toList());

        return new UserResponse.ProfileDTO(user, userCategoryList);
    }

    public void passwordCheck(UserRequest.PasswordCheckDTO requestDTO, CustomUserDetails userDetails) {
        User user = userJPARepository.findById(userDetails.getUser().getId())
                .orElseThrow(() -> new Exception404("해당 사용자가 존재하지 않습니다."));

        if (!passwordEncoder.matches(requestDTO.getPassword(), user.getPassword())) {
            throw new Exception400(null, "잘못된 비밀번호입니다.");
        }
    }

    @Transactional
    public UserResponse.ProfileDTO updateProfile(CustomUserDetails userDetails, UserRequest.ProfileUpdateDTO requestDTO, MultipartFile file) throws IOException {
        User user = userJPARepository.findById(userDetails.getUser().getId())
                .orElseThrow(() -> new Exception404("해당 사용자가 존재하지 않습니다."));

        String newProfileImageURL = user.getProfileImage();
        if (file != null) {
            if (user.getProfileImage() != null) {
                String profileImageURL = user.getProfileImage();
                String key = profileImageURL.split("/")[3];
                s3Uploader.deleteFile(key);
            }
            newProfileImageURL = s3Uploader.uploadFile(file);
        }
        user = user.updateProfile(requestDTO.toEntity(newProfileImageURL));

        List<String> userCategoryList = userInterestJPARepository.findAllById(user.getId()).stream()
                .map(interest -> interest.getInterest().getCategory())
                .collect(Collectors.toList());
        List<String> newUserCategoryList = requestDTO.getCategoryList();

        for (String newUserCategory : newUserCategoryList) {
            if (!userCategoryList.contains(newUserCategory)) {
                Interest interest = interestJPARepository.findByCategory(newUserCategory)
                        .orElseThrow(() -> new Exception400(null, "해당 관심사가 존재하지 않습니다."));
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
