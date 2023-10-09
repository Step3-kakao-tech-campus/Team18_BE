package com.example.demo.mentoringtest;


import com.example.demo.interest.Interest;
import com.example.demo.interest.InterestJPARepository;
import com.example.demo.mentoring.MentorPost;
import com.example.demo.mentoring.MentorPostJPARepostiory;
import com.example.demo.mentoring.MentorPostResponse;
import com.example.demo.mentoring.MentorPostService;
import com.example.demo.mentoring.contact.ContactJPARepository;
import com.example.demo.mentoring.contact.NotConnectedRegisterUser;
import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.user.UserJPARepository;
import com.example.demo.user.userInterest.UserInterest;
import com.example.demo.user.userInterest.UserInterestJPARepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.stream.Collectors;

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
    private UserInterestJPARepository userInterestJPARepository;
    @Autowired
    private ContactJPARepository contactJPARepository;

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
    @DisplayName("findMentorPostTest")
    void findMentorPostSaveTest() {

        //given
        User mentor = User.builder()
                .email("anjdal6612312364@gmail.com")
                .password("as123df1234!")
                .firstName("Jin123")
                .lastName("Seun123g")
                .country("Korea")
                .age(21)
                .role(Role.MENTOR)
                .build();

        User mentee1 = User.builder()
                .email("anjda22l6664@gmail.com")
                .password("asdf221234!")
                .firstName("Jin11")
                .lastName("Seung11")
                .country("Korea")
                .age(21)
                .role(Role.MENTEE)
                .build();

        User mentee2 = User.builder()
                .email("anjdal66111164@gmail.com")
                .password("asdf122222234!")
                .firstName("Jin22")
                .lastName("Seung22")
                .country("Korea")
                .age(21)
                .role(Role.MENTEE)
                .build();

        Interest interest1 = Interest.builder()
                .category("test1")
                .build();

        Interest interest2 = Interest.builder()
                .category("test2")
                .build();

        Interest interest3 = Interest.builder()
                .category("test3")
                .build();

        UserInterest userInterest1 = UserInterest.builder()
                .user(mentor)
                .interest(interest2)
                .build();

        UserInterest userInterest2 = UserInterest.builder()
                .user(mentee1)
                .interest(interest2)
                .build();

        UserInterest userInterest3 = UserInterest.builder()
                .user(mentee1)
                .interest(interest3)
                .build();

        UserInterest userInterest4 = UserInterest.builder()
                .user(mentee2)
                .interest(interest1)
                .build();

        UserInterest userInterest5 = UserInterest.builder()
                .user(mentee2)
                .interest(interest2)
                .build();

        MentorPost mentorPost3 = MentorPost.builder()
                .writer(mentor)
                .title("title")
                .content("content")
                .build();

        NotConnectedRegisterUser menteeNotConnected1 = NotConnectedRegisterUser.builder()
                .mentorPost(mentorPost3)
                .menteeUser(mentee1)
                .state(NotConnectedRegisterUser.State.AWAIT)
                .build();

        NotConnectedRegisterUser menteeNotConnected2 = NotConnectedRegisterUser.builder()
                .mentorPost(mentorPost3)
                .menteeUser(mentee2)
                .state(NotConnectedRegisterUser.State.AWAIT)
                .build();

        //when
        interestJPARepository.save(interest1);
        interestJPARepository.save(interest2);
        interestJPARepository.save(interest3);
        userJPARepository.save(mentor);
        userJPARepository.save(mentee1);
        userJPARepository.save(mentee2);
        mentorPostJPARepostiory.save(mentorPost3);
        userInterestJPARepository.save(userInterest1);
        userInterestJPARepository.save(userInterest2);
        userInterestJPARepository.save(userInterest3);
        userInterestJPARepository.save(userInterest4);
        userInterestJPARepository.save(userInterest5);
        contactJPARepository.save(menteeNotConnected1);
        contactJPARepository.save(menteeNotConnected2);

    }

    @Test
    void mentorPostServiceTest() throws Exception {
        MentorPostResponse.MentorPostDTO mentorPostFind = mentorPostService.findMentorPost(1);

        String responseBody = om.writeValueAsString(mentorPostFind);

        System.out.println("test : " + responseBody);
    }
}
