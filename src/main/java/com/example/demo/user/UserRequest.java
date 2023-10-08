package com.example.demo.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public class UserRequest {
    @Getter
    @Setter
    public static class EmailCheckDTO {
        @NotNull
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일을 올바르게 입력해주세요.")
        private String email;
    }

    @Getter
    @Setter
    public static class SignUpDTO {
        @NotNull
        private String firstName;

        @NotNull
        private String lastName;

        @NotNull
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일을 올바르게 입력해주세요.")
        private String email;

        @NotNull
        @Size(min = 8, max = 16, message = "8~16자 이내로 입력해주세요.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!~`<>,./?;:'\"\\[\\]{}\\\\()|_-])\\S*$", message = "영문 대/소문자, 숫자, 특수문자를 포함해주세요.")
        private String password;

        @NotNull
        private String country;

        private String introduction;

        @NotNull
        private int age;
        
        // 프로필 이미지의 경우, 추후 변경 예정
        private String profileImage;

        @NotNull
        private Role role;

        @NotNull
        private List<String> categoryList;

//        public User toEntity() {
//            return User.builder()
//                    .firstName(firstName)
//                    .lastName(lastName)
//                    .email(email)
//                    .password(password)
//                    .country(country)
//                    .introduction(introduction)
//                    .age(age)
//                    .profileImage(profileImage)
//                    .role(role)
//                    .build();
//        }
    }

    @Getter
    @Setter
    public static class LoginDTO {
        @NotNull
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일을 올바르게 입력해주세요.")
        private String email;

        @NotNull
        @Size(min = 8, max = 16, message = "8~16자 이내로 입력해주세요.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!~`<>,./?;:'\"\\[\\]{}\\\\()|_-])\\S*$", message = "영문 대/소문자, 숫자, 특수문자를 포함해주세요.")
        private String password;
    }
}