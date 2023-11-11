package com.example.demo;

import com.example.demo.config.errors.exception.Exception404;
import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.user.UserJPARepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

@SpringBootTest
@ActiveProfiles("test")
public class AccountTest {

    @Autowired
    private UserJPARepository userJPARepository;

    // saveTest
    @Test
    @DisplayName("account save test")
    void test() {
        User user = User.builder()
                .email("anjdal64@gmail.com")
                .password("asdf1234!")
                .firstName("Jin")
                .lastName("Seung")
                .country("Korea")
                .birthDate(LocalDate.of(1990, 1, 1))
                .role(Role.MENTOR)
                .phone("010-0000-0000")
                .build();

        // user save
        userJPARepository.save(user);

        // find
        User user1 = userJPARepository.findById(user.getId())
                .orElseThrow(() -> new Exception404("해당 사용자가 존재하지 않습니다."));
        Assertions.assertThat(user.getId())
                .isEqualTo(user1.getId());
    }
}
