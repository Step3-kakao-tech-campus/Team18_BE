package com.example.demo;

import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.user.UserJPARepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

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
                .age(21)
                .role(Role.MENTOR)
                .build();

        // user save
        userJPARepository.save(user);

        // find
        User user1 = userJPARepository.findById(user.getId());
        Assertions.assertThat(user.getId())
                .isEqualTo(user1.getId());
    }
}
