package com.example.demo.user;

import com.example.demo.config.errors.exception.Exception400;
import com.example.demo.config.errors.exception.Exception404;
import com.example.demo.interest.Interest;
import com.example.demo.interest.InterestJPARepository;
import com.example.demo.config.jwt.JWTTokenProvider;
import com.example.demo.refreshToken.RefreshToken;
import com.example.demo.refreshToken.RefreshTokenJPARepository;
import com.example.demo.refreshToken.TokenResponse;
import com.example.demo.user.userInterest.UserInterest;
import com.example.demo.user.userInterest.UserInterestJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

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
        User user = User.builder().firstName(requestDTO.getFirstName())
                        .lastName(requestDTO.getLastName())
                        .email(requestDTO.getEmail())
                        .password(passwordEncoder.encode(requestDTO.getPassword()))
                        .country(requestDTO.getCountry())
                        .introduction(requestDTO.getIntroduction())
                        .age(requestDTO.getAge())
                        .phone(requestDTO.getPhone())
                        .profileImage(requestDTO.getProfileImage())
                        .role(requestDTO.getRole())
                        .build();
        userJPARepository.save(user);

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

        TokenResponse.TokenDTO token = JWTTokenProvider.createToken(user);

        Optional<RefreshToken> refreshTokenInfo = refreshTokenJPARepository.findByUser(user);
        if (refreshTokenInfo.isPresent()) {
            refreshTokenInfo.get().updateRefreshToken(token.getRefreshToken());
        } else {
            RefreshToken newRefreshToken = RefreshToken.builder().user(user).refreshToken(token.getRefreshToken()).build();
            refreshTokenJPARepository.save(newRefreshToken);
        }

        return new UserResponse.LoginDTO(user, token);
    }

    public UserResponse.ProfileDTO findProfile(Integer id, User sessionUser) {
        User user;
        if (id == null) {
            user = userJPARepository.findById(sessionUser.getId())
                    .orElseThrow(() -> new Exception404("해당 사용자가 존재하지 않습니다."));
        } else {
            user = userJPARepository.findById(id)
                    .orElseThrow(() -> new Exception404("해당 사용자가 존재하지 않습니다."));
        }
        return new UserResponse.ProfileDTO(user);
    }

    @Transactional
    public UserResponse.ProfileDTO updateProfile(int id, UserRequest.ProfileUpdateDTO requestDTO) {
        User user = userJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("해당 사용자가 존재하지 않습니다."));

        User updateUser = User.builder()
                .firstName(requestDTO.getFirstName())
                .lastName(requestDTO.getLastName())
                .email(requestDTO.getEmail())
                .password(passwordEncoder.encode(requestDTO.getPassword()))
                .country(requestDTO.getCountry())
                .introduction(requestDTO.getIntroduction())
                .age(requestDTO.getAge())
                .phone(requestDTO.getPhone())
                .profileImage(requestDTO.getProfileImage())
                .role(requestDTO.getRole())
                .build();
        user.updateProfile(updateUser);

        userInterestJPARepository.deleteAllByUserId(id);

        List<UserInterest> updateUserInterestList = new ArrayList<>();
        List<String> categoryList = requestDTO.getCategoryList();
        for (String category : categoryList) {
            Interest interest = interestJPARepository.findByCategory(category)
                    .orElseThrow(() -> new Exception400("해당 관심사가 존재하지 않습니다."));
            UserInterest updateUserInterest = UserInterest.builder().user(user).interest(interest).build();
            updateUserInterestList.add(updateUserInterest);
        }
        userInterestJPARepository.saveAll(updateUserInterestList);

        return new UserResponse.ProfileDTO(user);
    }
}
