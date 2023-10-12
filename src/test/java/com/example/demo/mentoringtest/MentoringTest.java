package com.example.demo.mentoringtest;


import com.example.demo.RestDoc;
import com.example.demo.config.utils.StateEnum;
import com.example.demo.interest.Interest;
import com.example.demo.interest.InterestJPARepository;
import com.example.demo.mentoring.*;
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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class MentoringTest extends RestDoc {
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
//        userJPARepository.save(writer);
//        mentorPostJPARepostiory.save(mentorPost);
//        mentorPostJPARepostiory.save(mentorPost2);

        userJPARepository.save(writer);
        MentorPostRequest.CreateDTO mentorPostRequest = new MentorPostRequest.CreateDTO();
        mentorPostRequest.setTitle("title");
        mentorPostRequest.setContent("content");
        mentorPostRequest.setState(StateEnum.ACTIVE);
        mentorPostService.createMentorPost(mentorPostRequest, writer);

        MentorPost mentorPostFind = mentorPostJPARepostiory.findById(1);
        Assertions.assertThat(1)
                .isEqualTo(mentorPostFind.getId());
        Assertions.assertThat(mentorPostRequest.getTitle())
                .isEqualTo(mentorPostFind.getTitle());
        Assertions.assertThat(mentorPostRequest.getContent())
                .isEqualTo(mentorPostFind.getContent());
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

        User mentee_One = User.builder()
                .email("anjda22l6664@gmail.com")
                .password("asdf221234!")
                .firstName("mentee1")
                .lastName("Seung11")
                .country("Korea")
                .age(21)
                .role(Role.MENTEE)
                .build();

        User mentee_Two = User.builder()
                .email("anjdal66111164@gmail.com")
                .password("asdf122222234!")
                .firstName("mentee2")
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
                .user(mentee_One)
                .interest(interest2)
                .build();

        UserInterest userInterest3 = UserInterest.builder()
                .user(mentee_One)
                .interest(interest3)
                .build();

        UserInterest userInterest4 = UserInterest.builder()
                .user(mentee_Two)
                .interest(interest1)
                .build();

        UserInterest userInterest5 = UserInterest.builder()
                .user(mentee_Two)
                .interest(interest2)
                .build();

        UserInterest userInterest6 = UserInterest.builder()
                .user(mentor)
                .interest(interest3)
                .build();

        MentorPost mentorPost3 = MentorPost.builder()
                .writer(mentor)
                .title("title")
                .content("content")
                .build();

        NotConnectedRegisterUser menteeNotConnected1 = NotConnectedRegisterUser.builder()
                .mentorPost(mentorPost3)
                .menteeUser(mentee_One)
                .state(NotConnectedRegisterUser.State.AWAIT)
                .build();

        NotConnectedRegisterUser menteeNotConnected2 = NotConnectedRegisterUser.builder()
                .mentorPost(mentorPost3)
                .menteeUser(mentee_Two)
                .state(NotConnectedRegisterUser.State.AWAIT)
                .build();

        //when
        interestJPARepository.save(interest1);
        interestJPARepository.save(interest2);
        interestJPARepository.save(interest3);
        userJPARepository.save(mentor);
        userJPARepository.save(mentee_One);
        userJPARepository.save(mentee_Two);
        mentorPostJPARepostiory.save(mentorPost3);
        userInterestJPARepository.save(userInterest1);
        userInterestJPARepository.save(userInterest2);
        userInterestJPARepository.save(userInterest3);
        userInterestJPARepository.save(userInterest4);
        userInterestJPARepository.save(userInterest5);
        userInterestJPARepository.save(userInterest6);
        contactJPARepository.save(menteeNotConnected1);
        contactJPARepository.save(menteeNotConnected2);

    }

    @Test
    @DisplayName("updateMentorPostTest")
    void updateMentorPostTest()
    {
        User writer = User.builder()
            .email("anjdal6664@gmail.com")
            .password("asdf1234!")
            .firstName("Jin")
            .lastName("Seung")
            .country("Korea")
            .age(21)
            .role(Role.MENTOR)
            .build();

        MentorPostRequest.CreateDTO mentorPostRequest = new MentorPostRequest.CreateDTO();
        mentorPostRequest.setTitle("tittttttle");
        mentorPostRequest.setContent("content");

        MentorPostRequest.CreateDTO mentorPostUpdated = new MentorPostRequest.CreateDTO();
        mentorPostUpdated.setTitle("updated!!!!");
        mentorPostUpdated.setContent("contenttt");

        userJPARepository.save(writer);
        mentorPostService.createMentorPost(mentorPostRequest, writer);
        mentorPostService.updateMentorPost(mentorPostUpdated,1);

        MentorPost mentorPostFind = mentorPostJPARepostiory.findById(1);
        Assertions.assertThat(1)
                .isEqualTo(mentorPostFind.getId());
        Assertions.assertThat(mentorPostUpdated.getTitle())
                .isEqualTo(mentorPostFind.getTitle());
        Assertions.assertThat(mentorPostUpdated.getContent())
                .isEqualTo(mentorPostFind.getContent());
    }


    @Test
    void mentorPostServiceTest() throws Exception {
        MentorPostResponse.MentorPostDTO mentorPostFind = mentorPostService.findMentorPost(1);

        String responseBody = om.writeValueAsString(mentorPostFind);

        System.out.println("test : " + responseBody);
    }

    @WithUserDetails(value = "john@example.com")
    @Test
    public void CreateMentorPostTestMVC() throws Exception {

        MentorPostRequest.CreateDTO createDTO = new MentorPostRequest.CreateDTO();
        createDTO.setTitle("asfd");
        createDTO.setContent("afaffafa");

        String requestBody = om.writeValueAsString(createDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/mentorings/post")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        //resultActions.andExpect(jsonPath("$.success").value("true"));
        //resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "john@example.com")
    @Test
    public void GetMentorPostTestMVC() throws Exception {

        int id = 1;

        // when
        ResultActions resultActions = mvc.perform(
                get("/mentorings/post")
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);
    }

}
