package com.example.demo.user;

import com.example.demo.refreshToken.TokenResponse;
import lombok.Getter;
import lombok.Setter;

public class UserResponse {
    @Getter
    @Setter
    public static class LoginDTO {
        private UserDetailDTO userDetailDTO;
        private JWTToken jwtToken;

        public LoginDTO(User user, TokenResponse.TokenDTO token) {
            this.userDetailDTO = new UserDetailDTO(user);
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
            private String profileImage;
            private Role role;

            public UserDetailDTO(User user) {
                this.id = user.getId();
                this.email = user.getEmail();
                this.firstName = user.getFirstName();
                this.lastName = user.getLastName();
                this.country = user.getCountry();
                this.profileImage = user.getProfileImage();
                this.role = user.getRole();
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
    public static class ProfileDTO {
        private int id;
        private String profileImage;
        private String firstName;
        private String lastName;
        private String introduction;
        private String country;
        private String role;
        private String phone;
        private String email;

        public ProfileDTO(User user) {
            this.id = user.getId();
            this.profileImage = user.getProfileImage();
            this.firstName = user.getFirstName();
            this.lastName = user.getLastName();
            this.introduction = user.getIntroduction();
            this.country = user.getCountry();
            this.role = user.getRole().toString();
            this.phone = user.getPhone();
            this.email = user.getEmail();
        }
    }
}
