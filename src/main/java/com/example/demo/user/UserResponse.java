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
            private String firstname;
            private String lastname;
            private String country;
            private String profileImage;
            private Role role;

            public UserDetailDTO(User user) {
                this.id = user.getId();
                this.firstname = user.getFirstName();
                this.lastname = user.getLastName();
                this.country = user.getCountry();
                this.profileImage = user.getProfileImage();
                this.role = user.getRole();
            }
        }
    }
}
