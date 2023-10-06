package com.example.demo;

import com.example.demo.account.Account;
import com.example.demo.account.AccountJPARepository;
import com.example.demo.account.interest.Interest;
import com.example.demo.account.interest.InterestJPARepository;
import com.example.demo.account.userInterest.UserInterest;
import com.example.demo.account.userInterest.UserInterestJPARepository;
import com.example.demo.mentoring.MentorPost;
import com.example.demo.mentoring.MentorPostJPARepostiory;
import com.example.demo.mentoring.contact.ContactJPARepository;
import com.example.demo.mentoring.contact.ContactResponse;
import com.example.demo.mentoring.contact.ContactService;
import com.example.demo.mentoring.contact.NotConnectedRegisterUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class contactTest {

    @Autowired
    private MentorPostJPARepostiory mentorPostJPARepostiory;
    @Autowired
    private AccountJPARepository accountJPARepository;
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

    Account account1;

    @BeforeEach
    void setUp() {
        account1 = Account.builder()
                .id(1)
                .email("anjdal64@gmail.com")
                .password("asdf1234!")
                .firstname("aaa")
                .lastname("Seung")
                .country("Korea")
                .age(21)
                .role(Account.Role.MENTOR)
                .build();
        Account account2 = Account.builder()
                .id(2)
                .email("anjdal44@gmail.com")
                .password("asdf1234!")
                .firstname("bbb")
                .lastname("Seung")
                .country("Korea")
                .age(21)
                .role(Account.Role.MENTEE)
                .build();
        Account account3 = Account.builder()
                .id(3)
                .email("anjdal66@gmail.com")
                .password("asdf1234!")
                .firstname("ccc")
                .lastname("Seung")
                .country("Korea")
                .age(21)
                .role(Account.Role.MENTEE)
                .build();

        accountJPARepository.save(account1);
        accountJPARepository.save(account2);
        accountJPARepository.save(account3);

        Interest interest = Interest.builder()
                .id(1)
                .tag("K-POP")
                .build();

        interestJPARepository.save(interest);


        UserInterest userInterest1 = UserInterest.builder()
                .id(1)
                .user(account1)
                .interest(interest)
                .build();
        UserInterest userInterest2 = UserInterest.builder()
                .id(2)
                .user(account2)
                .interest(interest)
                .build();
        UserInterest userInterest3 = UserInterest.builder()
                .id(3)
                .user(account3)
                .interest(interest)
                .build();

        userInterestJPARepository.save(userInterest1);
        userInterestJPARepository.save(userInterest2);
        userInterestJPARepository.save(userInterest3);

        MentorPost mentorPost = MentorPost.builder()
                .writer(account1)
                .title("가나다라마바사")
                .build();

        MentorPost mentorPost2 = MentorPost.builder()
                .writer(account1)
                .title("아자차카타파하")
                .build();

        mentorPostJPARepostiory.save(mentorPost);
        mentorPostJPARepostiory.save(mentorPost2);


        NotConnectedRegisterUser mentee1 = NotConnectedRegisterUser.builder()
                .id(1)
                .mentorPost(mentorPost)
                .menteeUser(account2)
                .state(NotConnectedRegisterUser.State.AWAIT)
                .build();
        NotConnectedRegisterUser mentee2 = NotConnectedRegisterUser.builder()
                .id(2)
                .mentorPost(mentorPost)
                .menteeUser(account3)
                .state(NotConnectedRegisterUser.State.AWAIT)
                .build();
        // 문제점 : save 를 하기 위해 notConnected~ table 을 조회, mentorPost table 을 조회, account table 을 조회함
        // 나중에 고쳐야할 듯 하다.
        contactJPARepository.save(mentee1);
        contactJPARepository.save(mentee2);
    }

    @Test
    void test() throws Exception {

        List<ContactResponse.MentorPostDTO> responseDTOs = contactService.findAll(account1);

        List<UserInterest> menteeInterests = userInterestJPARepository.findAllById(2);
        List<UserInterest> userInterests = userInterestJPARepository.findAll();

        System.out.println(menteeInterests.size());

        String responseBody = om.writeValueAsString(responseDTOs);

        System.out.println("test : " + responseBody);

    }
}
