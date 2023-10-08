package com.example.demo.user;

import com.example.demo.interest.Interest;
import com.example.demo.interest.InterestJPARepository;
import com.example.demo.config.errors.exception.Exception400;
import com.example.demo.config.jwt.JWTTokenProvider;
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
    private final InterestJPARepository interestJPARepository;
    private final UserInterestJPARepository userInterestJPARepository;

    public void emailCheck(UserRequest.EmailCheckDTO requestDTO) {
        Optional<User> optionalUser = userJPARepository.findByEmail(requestDTO.getEmail());
        if (optionalUser.isPresent()) {
            throw new Exception400("동일한 이메일이 존재합니다.");
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
                        .profileImage(requestDTO.getProfileImage())
                        .role(requestDTO.getRole())
                        .build();
        userJPARepository.save(user);

        List<UserInterest> userInterestList = new ArrayList<>();
        List<String> categoryList = requestDTO.getCategoryList();
        for (String category : categoryList) {
            Interest interest = interestJPARepository.findByCategory(category)
                    .orElseThrow(() -> new Exception400("해당 관심사가 존재하지 않습니다."));
            UserInterest userInterest = UserInterest.builder().user(user).interest(interest).build();
            userInterestList.add(userInterest);
        }
        userInterestJPARepository.saveAll(userInterestList);
    }

    public UserResponse.LoginDTO login(UserRequest.LoginDTO requestDTO) {
        User user = userJPARepository.findByEmail(requestDTO.getEmail())
                .orElseThrow(() -> new Exception400("잘못된 이메일입니다."));

        if (!passwordEncoder.matches(requestDTO.getPassword(), user.getPassword())) {
            throw new Exception400("잘못된 비밀번호입니다.");
        }

        return new UserResponse.LoginDTO(user, JWTTokenProvider.create(user));
    }
}
