package com.example.demo.account;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AccountRequest {
    @Getter
    @Setter
    public static class SignUpDTO {
        @NotNull
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일을 올바르게 입력해주세요.")
        private String email;

        @NotNull
        @Size(min = 8, max = 16, message = "8~16자 이내로 입력해주세요.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!~`<>,./?;:'\"\\[\\]{}\\\\()|_-])\\S*$", message = "영문 대/소문자, 숫자, 특수문자를 포함해주세요.")
        private String password;

        @NotNull
        private String firstname;

        @NotNull
        private String lastname;

        @NotNull
        private String country;

        private String introduction;

        @NotNull
        private int age;

        private String profileImage;

        @NotNull
        private Account.Role role;

        public Account toEntity() {
            return Account.builder()
                    .email(email)
                    .password(password)
                    .firstname(firstname)
                    .lastname(lastname)
                    .country(country)
                    .introduction(introduction)
                    .age(age)
                    .profileImage(profileImage)
                    .role(role)
                    .build();
        }
    }
}
