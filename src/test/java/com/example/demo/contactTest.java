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
import io.swagger.models.Contact;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@ActiveProfiles("test")
@Sql("classpath:db/teardown.sql")
public class contactTest extends RestDoc {

    @Autowired
    private ContactService contactService;

    @Autowired
    private ObjectMapper om;

    @Test
    @WithUserDetails("john@example.com")
    @DisplayName("contact - mentor")
    void contactTest() throws Exception {

        // given

        // when
        ResultActions resultActions = mvc.perform(
                get("/contacts")
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.status").value("success"));

    }

    @Test
    @WithUserDetails("john@example.com")
    @DisplayName("contact, done - count")
    void countTest() throws Exception {

        // when
        ResultActions resultActions = mvc.perform(
                get("/contacts/postCounts")
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.status").value("success"));


    }
}
