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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
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
    @WithUserDetails("test3@example.com")
    @DisplayName("멘티 기준 화면 조회 테스트 코드")
    void contactMenteeTest() throws Exception {
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
    @WithUserDetails("test1@example.com")
    @DisplayName("멘토 기준 화면 조회 테스트 코드")
    void contactMentorTest() throws Exception {

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
    @WithUserDetails("test1@example.com")
    @DisplayName("멘토 기준 게시글 갯수 조회 테스트 코드")
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
    @WithUserDetails("test3@example.com")
    @DisplayName("멘티 기준 게시글 조회 테스트 코드")
    void countByMenteeTest() throws Exception {
        // given

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
    @WithUserDetails("test1@example.com")
    @DisplayName("멘토 : 신청 거부 기능 테스트 코드")
    void contactRefuseTest() throws Exception {
        // given
        ContactRequest.ContactRefuseDTO requestDTOs = new ContactRequest.ContactRefuseDTO();
        requestDTOs.setMentorPostId(1);
        requestDTOs.setMentorId(1);

        List<ContactRequest.ContactRefuseDTO.RefuseMenteeDTO> refuseMenteeDTOS = new ArrayList<>();
        refuseMenteeDTOS.add(new ContactRequest.ContactRefuseDTO.RefuseMenteeDTO(1));

        requestDTOs.setMentees(refuseMenteeDTOS);

        String requestBody = om.writeValueAsString(requestDTOs);

        System.out.println("테스트 : "+requestBody);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders.patch("/contacts/refuse")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // 테스트가 잘 됐는지 ( 값이 잘 바뀌는지 확인 )
        NotConnectedRegisterUser notConnectedRegisterUser = contactJPARepository.findById(1)
                .orElseThrow(() -> new Exception404("해당 사용자를 찾을 수 없습니다."));

        System.out.println("state 확인 : " + notConnectedRegisterUser.getState());
        // then
        // verify
        result.andExpect(jsonPath("$.status").value("success")); // 성공 테스트 확인
        // 값이 잘 들어가는지 확인
        Assertions.assertThat(notConnectedRegisterUser.getState()).isEqualTo(ContactStateEnum.REFUSE);
    }

    @Test
    @WithUserDetails("test1@example.com")
    @DisplayName("멘토 : 신청 수락 테스트 코드")
    void contactAccpetTest() throws Exception {
        // given
        ContactRequest.ContactAcceptDTO requestDTOs = new ContactRequest.ContactAcceptDTO();

        requestDTOs.setMentorPostId(1);
        requestDTOs.setMentorId(1);

        List<ContactRequest.ContactAcceptDTO.AcceptMenteeDTO> acceptMenteeDTOS = new ArrayList<>();
        acceptMenteeDTOS.add(new ContactRequest.ContactAcceptDTO.AcceptMenteeDTO(1));

        requestDTOs.setMentees(acceptMenteeDTOS);

        String requestBody = om.writeValueAsString(requestDTOs);

        System.out.println("테스트 : "+requestBody);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders.post("/contacts/accept")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // 테스트가 잘 됐는지 ( 값이 잘 바뀌는지 확인 )
        NotConnectedRegisterUser notConnectedRegisterUser = contactJPARepository.findById(1)
                .orElseThrow(() -> new Exception404("해당 사용자를 찾을 수 없습니다."));

        System.out.println("state 확인 : " + notConnectedRegisterUser.getState());

        // 테스트가 잘 됐는지 ( 값이 잘 들어가는지 확인 )
        ConnectedUser connectedUser = doneJPARepository.findById(4)
                .orElseThrow(() -> new Exception404("해당 사용자를 찾을 수 없습니다."));

        System.out.println("connectedUser 확인 : " + connectedUser.getId());

        // verify
        result.andExpect(jsonPath("$.status").value("success")); // 성공 테스트 확인
        // 값이 잘 들어가는지 확인
        Assertions.assertThat(notConnectedRegisterUser.getState()).isEqualTo(ContactStateEnum.ACCEPT);
        Assertions.assertThat(connectedUser.getId()).isEqualTo(4);
    }

    @Test
    @WithUserDetails("test4@example.com")
    @DisplayName("멘티 : 신청 생성 테스트 코드")
    void createTest() throws Exception {
        // given
        ContactRequest.ContactCreateDTO contactCreateDTO = new ContactRequest.ContactCreateDTO();
        // mentor, mentorPost, mentee id 지정
        contactCreateDTO.setMentorPostId(3);
        contactCreateDTO.setMentorId(2);
        contactCreateDTO.setMenteeId(4);

        String requestBody = om.writeValueAsString(contactCreateDTO);

        System.out.println("테스트 requestBody : "+requestBody);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders.post("/contacts")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 responseBody : "+responseBody);

        // 테스트가 잘 됐는지 ( 값이 잘 바뀌는지 확인 )
        NotConnectedRegisterUser notConnectedRegisterUser = contactJPARepository.findById(5)
                .orElseThrow(() -> new Exception404("해당 사용자를 찾을 수 없습니다."));

        // then
        // verify
        result.andExpect(jsonPath("$.status").value("success")); // 성공 테스트 확인
        // 값이 잘 들어가는지 확인
        Assertions.assertThat(notConnectedRegisterUser.getState()).isEqualTo(ContactStateEnum.AWAIT);

    }

    @Test
    @WithUserDetails("test3@example.com")
    @DisplayName("멘티 : 신청 취소 테스트 코드")
    void deleteTest() throws Exception {
        // given
        List<Integer> connectionIds = Arrays.asList(1, 2); // 실제로 취소할 연결 ID 목록

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders.delete("/contacts")
                        .param("connectionId", connectionIds.stream().map(String::valueOf).toArray(String[]::new)) // 정수 목록을 문자열 배열로 변환
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 responseBody : "+responseBody);

        // then
        result.andExpect(jsonPath("$.status").value("success")); // 성공 테스트 확인
    }
}
