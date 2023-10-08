package com.example.demo;

import com.example.demo.interest.Interest;
import com.example.demo.interest.InterestJPARepository;
import com.example.demo.mentoring.MentorPost;
import com.example.demo.mentoring.MentorPostJPARepostiory;
import com.example.demo.mentoring.contact.ContactJPARepository;
import com.example.demo.mentoring.contact.ContactResponse;
import com.example.demo.mentoring.contact.ContactService;
import com.example.demo.mentoring.contact.NotConnectedRegisterUser;
import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.user.UserJPARepository;
import com.example.demo.user.userInterest.UserInterest;
import com.example.demo.user.userInterest.UserInterestJPARepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class contactTest {

    @Autowired
    private MentorPostJPARepostiory mentorPostJPARepostiory;
    @Autowired
    private UserJPARepository userJPARepository;
    @Autowired
    private ContactJPARepository contactJPARepository;
    @Autowired
    private UserInterestJPARepository userInterestJPARepository;
    @Autowired
    private InterestJPARepository interestJPARepository;

    @Autowired
    private ContactService contactService;

    @Autowired
    private ObjectMapper om;

    User user1;

    @BeforeEach
    void setUp() {
        user1 = User.builder()
                .email("anjdal65@gmail.com")
                .password("asdf1234!")
                .firstName("aaa")
                .lastName("Seung")
                .country("Korea")
                .age(21)
                .role(Role.MENTOR)
                .build();
        User user2 = User.builder()
                .email("anjdal44@gmail.com")
                .password("asdf1234!")
                .firstName("bbb")
                .lastName("Seung")
                .country("Korea")
                .age(21)
                .role(Role.MENTEE)
                .build();
        User user3 = User.builder()
                .email("anjdal66@gmail.com")
                .password("asdf1234!")
                .firstName("ccc")
                .lastName("Seung")
                .country("Korea")
                .age(21)
                .role(Role.MENTEE)
                .build();

        userJPARepository.save(user1);
        userJPARepository.save(user2);
        userJPARepository.save(user3);

        Interest interest = Interest.builder()
                .category("K-POP")
                .build();

        interestJPARepository.save(interest);


        UserInterest userInterest1 = UserInterest.builder()
                .user(user1)
                .interest(interest)
                .build();
        UserInterest userInterest2 = UserInterest.builder()
                .user(user2)
                .interest(interest)
                .build();
        UserInterest userInterest3 = UserInterest.builder()
                .user(user3)
                .interest(interest)
                .build();

        userInterestJPARepository.save(userInterest1);
        userInterestJPARepository.save(userInterest2);
        userInterestJPARepository.save(userInterest3);

        MentorPost mentorPost = MentorPost.builder()
                .writer(user1)
                .title("가나다라마바사")
                .build();

        MentorPost mentorPost2 = MentorPost.builder()
                .writer(user1)
                .title("아자차카타파하")
                .build();

        mentorPostJPARepostiory.save(mentorPost);
        mentorPostJPARepostiory.save(mentorPost2);


        NotConnectedRegisterUser mentee1 = NotConnectedRegisterUser.builder()
                .mentorPost(mentorPost)
                .menteeUser(user2)
                .state(NotConnectedRegisterUser.State.AWAIT)
                .build();
        NotConnectedRegisterUser mentee2 = NotConnectedRegisterUser.builder()
                .mentorPost(mentorPost)
                .menteeUser(user3)
                .state(NotConnectedRegisterUser.State.AWAIT)
                .build();
        // 문제점 : save 를 하기 위해 notConnected~ table 을 조회, mentorPost table 을 조회, account table 을 조회함
        // 나중에 고쳐야할 듯 하다.
        contactJPARepository.save(mentee1);
        contactJPARepository.save(mentee2);
    }

    @Test
    void test() throws Exception {

        List<ContactResponse.MentorPostDTO> responseDTOs = contactService.findAll(user1);

        String responseBody = om.writeValueAsString(responseDTOs);

        System.out.println("test : " + responseBody);

    }
}
