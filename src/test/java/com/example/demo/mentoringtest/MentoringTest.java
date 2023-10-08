package com.example.demo.mentoringtest;

import com.example.demo.account.Account;
import com.example.demo.account.AccountJPARepository;
import com.example.demo.mentoring.MentorPost;
import com.example.demo.mentoring.MentorPostJPARepostiory;
import com.example.demo.mentoring.MentorPostResponse;
import com.example.demo.mentoring.MentorPostService;
import com.example.demo.mentoring.contact.ContactResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MentoringTest {
    @Autowired
    private AccountJPARepository accountJPARepository;
    @Autowired
    private MentorPostJPARepostiory mentorPostJPARepostiory;

    @Autowired
    private MentorPostService mentorPostService;

    @Autowired
    private ObjectMapper om;


    @Test
    @DisplayName("CreateMentorPostTest")
    void CreateMentorPostTest() {

        //given
        Account writer = Account.builder()
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
                .writer(writer)
                .title("title")
                .content("content")
                .build();

        // then
        accountJPARepository.save(writer);
        mentorPostJPARepostiory.save(mentorPost);

        MentorPost mentorPostFind = mentorPostJPARepostiory.findById(mentorPost.getId());
        System.out.println("createDateTest  :  " + mentorPostFind.getCreatedDate());
        Assertions.assertThat(mentorPost.getId())
                .isEqualTo(mentorPostFind.getId());
    }

    @Test
    void test() throws Exception {

        List<MentorPostResponse.MentorPostDTO> responseDTOs = mentorPostService.findAllMentorPost();

        String responseBody = om.writeValueAsString(responseDTOs);

        System.out.println("test : " + responseBody);

    }
}
