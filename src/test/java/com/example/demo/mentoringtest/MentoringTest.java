package com.example.demo.mentoringtest;

import com.example.demo.account.Account;
import com.example.demo.account.AccountJPARepository;
import com.example.demo.mentoring.MentorPost;
import com.example.demo.mentoring.MentorPostJPARepostiory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MentoringTest {
    @Autowired
    private AccountJPARepository accountJPARepository;
    @Autowired
    private MentorPostJPARepostiory mentorPostJPARepostiory;


    @Test
    @DisplayName("CreateMentorPostTest")
    void CreateMentorPostTest() {

        //given
        Account writer = Account.builder()
                .uid(1)
                .email("anjdal64@gmail.com")
                .password("asdf1234!")
                .firstname("Jin")
                .lastname("Seung")
                .country("Korea")
                .age(21)
                .role(Account.Role.MENTOR)
                .build();

        //when
        MentorPost mentorPost = MentorPost.builder()
                .pid(1)
                .writer(writer)
                .title("title")
                .context("context")
                .build();

        // then
        accountJPARepository.save(writer);
        mentorPostJPARepostiory.save(mentorPost);

        MentorPost mentorPostFind = mentorPostJPARepostiory.findById(mentorPost.getPid()).get();
        Assertions.assertThat(mentorPost.getPid())
                .isEqualTo(mentorPostFind.getPid());
    }
}
