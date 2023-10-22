package com.example.demo;

import com.example.demo.config.errors.exception.Exception404;
import com.example.demo.mentoring.contact.*;
import com.example.demo.mentoring.done.ConnectedUser;
import com.example.demo.mentoring.done.DoneJPARepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@ActiveProfiles("test")
@Sql("classpath:db/teardown.sql")
public class contactTest extends RestDoc {

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ContactJPARepository contactJPARepository;

    @Autowired
    private DoneJPARepository doneJPARepository;

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

    @Test
    @WithUserDetails("john@example.com")
    @DisplayName("contact - Accept - Test")
    void contactAccpetTest() throws Exception {
        // given
        ContactRequest.AcceptDTO requestDTOs = new ContactRequest.AcceptDTO();

        requestDTOs.setMentorPostId(1);

        List<ContactRequest.AcceptDTO.MentorAndMenteeDTO> mentorAndMenteeDTOs = new ArrayList<>();
        mentorAndMenteeDTOs.add(new ContactRequest.AcceptDTO.MentorAndMenteeDTO(1, 3));

        requestDTOs.setMentorsAndMentees(mentorAndMenteeDTOs);

        String requestBody = om.writeValueAsString(requestDTOs);

        System.out.println("테스트 : "+requestBody);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders.post("/contacts/1/accept")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // 테스트가 잘 됐는지 ( 값이 잘 바뀌는지 확인 )
        NotConnectedRegisterUser notConnectedRegisterUser = contactJPARepository.findByMentorPostIdAndMenteeUserId(1, 3)
                .orElseThrow(() -> new Exception404("해당 사용자를 찾을 수 없습니다."));

        System.out.println("state 확인 : " + notConnectedRegisterUser.getState());

        // 테스트가 잘 됐는지 ( 값이 잘 들어가는지 확인 )
        ConnectedUser connectedUser = doneJPARepository.findById(4)
                .orElseThrow(() -> new Exception404("해당 사용자를 찾을 수 없습니다."));

        System.out.println("connectedUser 확인 : " + connectedUser.getId());

        // verify
        result.andExpect(jsonPath("$.status").value("success")); // 성공 테스트 확인
        // 값이 잘 들어가는지 확인
        Assertions.assertThat(notConnectedRegisterUser.getState()).isEqualTo(NotConnectedRegisterUser.State.ACCEPT);
        Assertions.assertThat(connectedUser.getId()).isEqualTo(4);
    }
}
