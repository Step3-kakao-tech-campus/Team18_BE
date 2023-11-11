package com.example.demo.user;

import com.example.demo.interest.Interest;
import com.example.demo.refreshToken.TokenResponse;
import com.example.demo.user.userInterest.UserInterest;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class UserResponse {
    @Getter
    @Setter
    public static class LoginDTO {
        private UserDetailDTO userDetailDTO;
        private JWTToken jwtToken;

        public LoginDTO(User user, List<String> userCategoryList, TokenResponse.TokenDTO token) {
            this.userDetailDTO = new UserDetailDTO(user, userCategoryList);
            this.jwtToken = new JWTToken(token);
        }

        @Getter
        @Setter
        public class UserDetailDTO {
            private int id;
            private String firstName;
            private String lastName;
            private String email;
            private String country;
            private LocalDate birthDate;
            private String phone;
            private String profileImage;
            private Role role;
            private List<String> categorylist;

            public UserDetailDTO(User user, List<String> userCategoryList) {
                this.id = user.getId();
                this.email = user.getEmail();
                this.firstName = user.getFirstName();
                this.lastName = user.getLastName();
                this.country = user.getCountry();
                this.birthDate = user.getBirthDate();
                this.phone = user.getPhone();
                this.profileImage = user.getProfileImage();
                this.role = user.getRole();
                this.categorylist = userCategoryList;
            }
        }

        @Getter
        @Setter
        public class JWTToken {
            private String accessToken;
            private String refreshToken;

            public JWTToken(TokenResponse.TokenDTO token) {
                this.accessToken = token.getAccessToken();
                this.refreshToken = token.getRefreshToken();
            }
        }
    }

    @Getter
    @Setter
    public static class SimpleProfileDTO {
        private int id;
        private String profileImage;
        private String firstName;
        private String lastName;
        private String email;

        public SimpleProfileDTO(User user) {
            this.id = user.getId();
            this.profileImage = user.getProfileImage();
            this.firstName = user.getFirstName();
            this.lastName = user.getLastName();
            this.email = user.getEmail();
        }
    }

    @Getter
    @Setter
    public static class ProfileDTO {
        private int id;
        private String email;
        private String firstName;
        private String lastName;
        private String country;
        private String introduction;
        private LocalDate birthDate;
        private String profileImage;
        private String phone;
        private Role role;
        private List<String> categoryList;

        public ProfileDTO(User user, List<String> userCategoryList) {
            this.id = user.getId();
            this.email = user.getEmail();
            this.firstName = user.getFirstName();
            this.lastName = user.getLastName();
            this.country = user.getCountry();
            this.introduction = user.getIntroduction();
            this.birthDate = user.getBirthDate();
            this.phone = user.getPhone();
            this.profileImage = user.getProfileImage();
            this.role = user.getRole();
            this.categoryList = userCategoryList;
        }
    }
}
