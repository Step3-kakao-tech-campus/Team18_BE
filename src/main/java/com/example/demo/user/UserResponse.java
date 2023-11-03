package com.example.demo.user;

import lombok.Getter;
import lombok.Setter;

public class UserResponse {
    @Getter
    @Setter
    public static class LoginDTO {
        private UserDetailDTO userDetailDTO;
        private String JWTToken;

        public LoginDTO(User user, String JWTToken) {
            this.userDetailDTO = new UserDetailDTO(user);
            this.JWTToken = JWTToken;
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
