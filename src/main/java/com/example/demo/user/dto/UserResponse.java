package com.example.demo.user.dto;

import com.example.demo.user.domain.Role;
import com.example.demo.user.domain.User;
//import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

public class UserResponse {
    @Schema(description = "로그인 응답 DTO")
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
            private String email;
            private String firstName;
            private String lastName;
            private String country;
            private LocalDate birthDate;
            private String profileImage;
            private String phone;
            private Role role;
            private List<String> categorylist;

            public UserDetailDTO(User user, List<String> userCategoryList) {
                this.id = user.getId();
                this.email = user.getEmail();
                this.firstName = user.getFirstName();
                this.lastName = user.getLastName();
                this.country = user.getCountry();
                this.birthDate = user.getBirthDate();
                this.profileImage = user.getProfileImage();
                this.phone = user.getPhone();
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

//    @ApiModel(value = "간단한 프로필 정보 응답 DTO")
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

//    @ApiModel(value = "프로필 정보 응답 DTO")
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
        private List<String> categorylist;

        public ProfileDTO(User user, List<String> userCategoryList) {
            this.id = user.getId();
            this.email = user.getEmail();
            this.firstName = user.getFirstName();
            this.lastName = user.getLastName();
            this.country = user.getCountry();
            this.introduction = user.getIntroduction();
            this.birthDate = user.getBirthDate();
            this.profileImage = user.getProfileImage();
            this.phone = user.getPhone();
            this.role = user.getRole();
            this.categorylist = userCategoryList;
        }
    }
}
