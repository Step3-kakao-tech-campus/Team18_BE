package com.example.demo;

import com.example.demo.account.Account;
import com.example.demo.account.AccountJPARepository;
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
    private AccountJPARepository accountJPARepository;

    // saveTest
    @Test
    @DisplayName("account save test")
    void test() {
        Account account = Account.builder()
                .uid(1)
                .email("anjdal64@gmail.com")
                .password("asdf1234!")
                .firstname("Jin")
                .lastname("Seung")
                .country("Korea")
                .age(21)
                .role(Account.Role.MENTOR)
                .build();

        // account save
        accountJPARepository.save(account);

        // find
        Account account1 = accountJPARepository.findById(account.getUid()).get();
        Assertions.assertThat(account.getUid())
                .isEqualTo(account1.getUid());
    }
}
