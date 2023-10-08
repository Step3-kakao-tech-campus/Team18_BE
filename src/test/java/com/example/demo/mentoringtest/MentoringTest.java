package com.example.demo.mentoringtest;


import com.example.demo.interest.InterestJPARepository;
import com.example.demo.mentoring.MentorPost;
import com.example.demo.mentoring.MentorPostJPARepostiory;
import com.example.demo.mentoring.MentorPostResponse;
import com.example.demo.mentoring.MentorPostService;
import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.user.UserJPARepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class MentoringTest {
    @Autowired
    private UserJPARepository userJPARepository;
    @Autowired
    private MentorPostJPARepostiory mentorPostJPARepostiory;
    @Autowired
    private InterestJPARepository interestJPARepository;
    @Autowired
    private MentorPostService mentorPostService;

    @Autowired
    private ObjectMapper om;


    @Test
    @DisplayName("CreateMentorPostTest")
    void CreateMentorPostTest() {

        //given
        User writer = User.builder()
                .email("anjdal6664@gmail.com")
                .password("asdf1234!")
                .firstName("Jin")
                .lastName("Seung")
                .country("Korea")
                .age(21)
                .role(Role.MENTOR)
                .build();

        //when
        MentorPost mentorPost = MentorPost.builder()
                .writer(writer)
                .title("title")
                .content("content")
                .build();

        MentorPost mentorPost2 = MentorPost.builder()
                .writer(writer)
                .title("title2")
                .content("content2")
                .build();

        // then
        userJPARepository.save(writer);
        mentorPostJPARepostiory.save(mentorPost);
        mentorPostJPARepostiory.save(mentorPost2);

        MentorPost mentorPostFind = mentorPostJPARepostiory.findById(mentorPost.getId());
        Assertions.assertThat(mentorPost.getId())
                .isEqualTo(mentorPostFind.getId());
    }

    @Test
    void test() throws Exception {

        List<MentorPostResponse.MentorPostAllDTO> responseDTOs = mentorPostService.findAllMentorPost(0);

        String responseBody = om.writeValueAsString(responseDTOs);

        System.out.println("test : " + responseBody);

    }
}
