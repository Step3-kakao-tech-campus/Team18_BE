package com.example.demo.user.dto;

import com.example.demo.user.domain.Role;
import com.example.demo.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

public class UserRequest {
    @Schema(description = "이메일 중복 확인 요청 DTO")
    @Getter
    @Setter
    public static class EmailCheckDTO {
        @Schema(description = "이메일", nullable = false, example = "garden@garden.com")
        @NotNull
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일을 올바르게 입력해주세요.")
        private String email;
    }

    @Schema(description = "회원가입 요청 DTO")
    @Getter
    @Setter
    public static class SignUpDTO {
        @Schema(description = "이름", nullable = false, example = "Anna")
        @NotNull(message = "이름을 입력해주세요.")
        private String firstName;

        @Schema(description = "성", nullable = false, example = "Lee")
        @NotNull(message = "성을 입력해주세요.")
        private String lastName;

        @Schema(description = "이메일", nullable = false, example = "garden@garden.com")
        @NotNull
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일을 올바르게 입력해주세요.")
        private String email;

        @Schema(description = "비밀번호", nullable = false, example = "garden123!")
        @NotNull
        @Size(min = 8, max = 16, message = "8~16자 이내로 입력해주세요.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!~`<>,./?;:'\"\\[\\]{}\\\\()|_-])\\S*$", message = "영문 대/소문자, 숫자, 특수문자를 포함해주세요.")
        private String password;

        @Schema(description = "국가 코드", nullable = false, example = "US")
        @NotNull(message = "국가를 선택해주세요.")
        private String country;

        @Schema(description = "자기 소개", nullable = true, example = "Hello, My name is Anna Lee. I'm interested in Korean.")
        private String introduction;

        @Schema(description = "생년월일", nullable = false, example = "2000-01-01")
        @NotNull(message = "생년월일을 입력해주세요.")
        private LocalDate birthDate;

        @Schema(description = "연락처", nullable = false, example = "010-1234-5678")
        @NotNull(message = "연락처를 입력해주세요.")
        @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$")
        private String phone;

        @Schema(description = "역할", nullable = false, example = "MENTOR")
        @NotNull(message = "역할을 선택해주세요.")
        private Role role;

        @Schema(description = "관심분야", nullable = false)
        @NotNull(message = "관심 분야를 선택해주세요.")
        @Size(min = 1, max = 3)
        private List<String> categoryList;

        public User toEntity(String profileImageURL) {
            return User.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email)
                    .password(password)
                    .country(country)
                    .introduction(introduction)
                    .birthDate(birthDate)
                    .phone(phone)
                    .profileImage(profileImageURL)
                    .role(role)
                    .build();
        }
    }

    @Schema(description = "로그인 요청 DTO")
    @Getter
    @Setter
    public static class LoginDTO {
        @Schema(description = "이메일", nullable = false, example = "garden@garden.com")
        @NotNull
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일을 올바르게 입력해주세요.")
        private String email;

        @Schema(description = "비밀번호", nullable = false, example = "garden123!")
        @NotNull
        @Size(min = 8, max = 16, message = "8~16자 이내로 입력해주세요.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!~`<>,./?;:'\"\\[\\]{}\\\\()|_-])\\S*$", message = "영문 대/소문자, 숫자, 특수문자를 포함해주세요.")
        private String password;
    }

    @Schema(description = "사용자 정보 수정 시, 본인 확인을 위한 비밀번호 요청 DTO")
    @Getter
    @Setter
    public static class PasswordCheckDTO {
        @Schema(description = "비밀번호", nullable = false, example = "garden123!")
        @NotNull
        @Size(min = 8, max = 16, message = "8~16자 이내로 입력해주세요.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!~`<>,./?;:'\"\\[\\]{}\\\\()|_-])\\S*$", message = "영문 대/소문자, 숫자, 특수문자를 포함해주세요.")
        private String password;
    }

    @Schema(description = "사용자 프로필 정보 수정 요청 DTO")
    @Getter
    @Setter
    public static class ProfileUpdateDTO {
        @Schema(description = "이름", nullable = false, example = "Anna")
        @NotNull(message = "이름을 입력해주세요.")
        @Size(max = 50, message = "50자 제한입니다.")
        private String firstName;

        @Schema(description = "성", nullable = false, example = "Lee")
        @NotNull(message = "성을 입력해주세요.")
        @Size(max = 50, message = "50자 제한입니다.")
        private String lastName;

        @Schema(description = "비밀번호", nullable = false, example = "garden123!")
        @NotNull
        @Size(min = 8, max = 16, message = "8~16자 이내로 입력해주세요.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!~`<>,./?;:'\"\\[\\]{}\\\\()|_-])\\S*$", message = "영문 대/소문자, 숫자, 특수문자를 포함해주세요.")
        private String password;

        @Schema(description = "국가 코드", nullable = false, example = "US")
        @NotNull(message = "국가를 선택해주세요.")
        private String country;

<<<<<<< HEAD:src/main/java/com/example/demo/user/dto/UserRequest.java
        @Schema(description = "자기 소개", nullable = true, example = "Hello, My name is Anna Lee. I'm interested in Korean.")
=======
        @Size(max = 300, message = "300자 제한입니다.")
>>>>>>> origin/weekly:src/main/java/com/example/demo/user/UserRequest.java
        private String introduction;

        @Schema(description = "생년월일", nullable = false, example = "2000-01-01")
        @NotNull(message = "생년월일을 입력해주세요.")
        private LocalDate birthDate;

        @Schema(description = "연락처", nullable = false, example = "010-1234-5678")
        @NotNull(message = "연락처를 입력해주세요.")
        @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$")
        private String phone;

        @Schema(description = "역할", nullable = false, example = "MENTOR")
        @NotNull(message = "역할을 선택해주세요.")
        private Role role;

        @Schema(description = "관심분야", nullable = false)
        @NotNull(message = "관심 분야를 선택해주세요.")
        @Size(min = 1, max = 3)
        private List<String> categoryList;

        public User toEntity(String profileImageURL) {
            return User.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .password(password)
                    .country(country)
                    .introduction(introduction)
                    .birthDate(birthDate)
                    .phone(phone)
                    .profileImage(profileImageURL)
                    .role(role)
                    .build();
        }
    }
}
