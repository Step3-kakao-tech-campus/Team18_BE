package com.example.demo;

import com.example.demo.config.errors.exception.Exception400;
import com.example.demo.mentoring.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@ActiveProfiles("test")
@Sql("classpath:db/teardown.sql")
public class MentoringTest extends RestDoc {

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MentorPostJPARepostiory mentorPostJPARepostiory;

    @Test
    @DisplayName("멘토링 화면 조회 테스트 코드")
    void MentoringTestByMentor() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(
                get("/mentorings")
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("멘토링 화면 조회 테스트 : "+responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.status").value("success"));
    }

    @Test
    @DisplayName("카테고리를 적용한 멘토링 화면 조회 테스트 코드 : Title")
    void MentoringTestByCategoryTitle() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(
                get("/mentorings")
                        .param("category", "TITLE")
                        .param("search", "Teaching")
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("카테고리 멘토링 화면 조회 테스트 : "+responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.status").value("success"));
    }

    @Test
    @DisplayName("카테고리를 적용한 멘토링 화면 조회 테스트 코드 : Writer")
    void MentoringTestByCategoryWriter() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(
                get("/mentorings")
                        .param("category", "WRITER")
                        .param("search", "John")
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.status").value("success"));
    }

    @Test
    @DisplayName("카테고리를 적용한 멘토링 화면 조회 테스트 코드 : Interest")
    void MentoringTestByCategoryInterest() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(
                get("/mentorings")
                        .param("category", "INTEREST")
                        .param("search", "K-POP")
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.status").value("success"));
    }

    @Test
    @DisplayName("세부 페이지 조회 테스트")
    void MentoringTestUpdate() throws Exception {
        // given
        int id = 1;

        // when
        ResultActions resultActions = mvc.perform(
                get("/mentorings/" + id)
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.status").value("success"));

    }

    @Test
    @WithUserDetails("test1@example.com")
    @DisplayName("게시글 수정 테스트")
    void MentoringTestDetails() throws Exception {
        // given
        int pid = 1;

        // requestDTO : title, content
        MentorPostRequest.CreateMentorPostDTO requestDTO = new MentorPostRequest.CreateMentorPostDTO();
        requestDTO.setTitle("바뀐 제목111");
        requestDTO.setContent("바뀐 내용111");

        String requestBody = om.writeValueAsString(requestDTO);

        System.out.println("테스트 : "+requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                put("/mentorings/" + pid)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // pid 에 해당하는 게시글 조회
        MentorPost mentorPost = mentorPostJPARepostiory.findById(pid).orElseThrow(
                () -> new Exception400(null, "해당 게시글이 없습니다."));

        // 데이터 확인
        System.out.println(mentorPost.getTitle());
        System.out.println(mentorPost.getContent());

        // 조회한 게시글의 제목과 내용이 일치하는지 확인
        resultActions.andExpect(jsonPath("$.status").value("success"));

    }

    @Test
    @WithUserDetails("test1@example.com")
    @DisplayName("게시글 삭제 테스트 코드")
    void MentoringTestDelete() throws Exception {
        // given
        int pid = 1;

        // when
        ResultActions resultActions = mvc.perform(
                delete("/mentorings/" + pid)
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // pid 에 해당하는 게시글 조회
        MentorPost mentorPost = mentorPostJPARepostiory.findById(pid).orElse(null);

        // 조회한 게시글의 제목과 내용이 일치하는지 확인
        resultActions.andExpect(jsonPath("$.status").value("success"));
        assertNull(mentorPost);
    }

    @Test
    @WithUserDetails("test1@example.com")
    @DisplayName("게시글 Done 테스트 코드")
    void MentoringTestDone() throws Exception {
        // given
        int pid = 1;
        // request
        MentorPostRequest.StateDTO requestDTO = new MentorPostRequest.StateDTO();
        requestDTO.setMentorPostStateEnum(MentorPostStateEnum.DONE);

        String requestBody = om.writeValueAsString(requestDTO);

        System.out.println("테스트 : "+requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                patch("/mentorings/" + pid + "/done")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // pid 에 해당하는 게시글 조회
        MentorPost mentorPost = mentorPostJPARepostiory.findById(pid).orElseThrow(
                () -> new Exception400(null, "해당 게시글이 없습니다."));

        System.out.println(mentorPost.getState());

        // 조회한 게시글의 제목과 내용이 일치하는지 확인
        resultActions.andExpect(jsonPath("$.status").value("success"));
        assertEquals(mentorPost.getState(), MentorPostStateEnum.DONE);
    }

    @Test
    @WithUserDetails("test3@example.com")
    @DisplayName("멘티가 게시글을 수정하려고 하는 경우 테스트")
    void MentoringTest2() throws Exception {
        int pid = 1;

        // requestDTO : title, content
        MentorPostRequest.CreateMentorPostDTO requestDTO = new MentorPostRequest.CreateMentorPostDTO();
        requestDTO.setTitle("바뀐 제목111");
        requestDTO.setContent("바뀐 내용111");

        String requestBody = om.writeValueAsString(requestDTO);

        System.out.println("테스트 : "+requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                put("/mentorings/" + pid)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // pid 에 해당하는 게시글 조회
        MentorPost mentorPost = mentorPostJPARepostiory.findById(pid).orElseThrow(
                () -> new Exception400(null, "해당 게시글이 없습니다."));

        // 데이터 확인
        System.out.println(mentorPost.getTitle());
        System.out.println(mentorPost.getContent());

        // 조회한 게시글의 제목과 내용이 일치하는지 확인
        resultActions.andExpect(jsonPath("$.status").value("error"));
    }

    @Test
    @WithUserDetails("test7@example.com")
    @DisplayName("멘토가 게시글 생성할때 테스트")
    void MentoringPostTest() throws Exception {
        // requestDTO : title, content
        MentorPostRequest.CreateMentorPostDTO requestDTO = new MentorPostRequest.CreateMentorPostDTO();
        requestDTO.setTitle("제목post");
        requestDTO.setContent("내용post");

        String requestBody = om.writeValueAsString(requestDTO);

        System.out.println("테스트 : "+requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                post("/mentorings")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        resultActions.andExpect(jsonPath("$.status").value("success"));
    }

    @Test
    @WithUserDetails("test7@example.com")
    @DisplayName("멘토가 게시글 생성할때 내용 301자 이상테스트")
    void MentoringPostFail301Test() throws Exception {
        String testString = "LLLLorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla facilisi. Sed non augue eget metus suscipit semper. Vestibulum id mi nec sapienLorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla facilisi. Sed non augue eget metus suscipit semper. Vestibulum id mi nec sapienmi nec sapiegg";
        //300자
        // requestDTO : title, content
        MentorPostRequest.CreateMentorPostDTO requestDTO = new MentorPostRequest.CreateMentorPostDTO();
        requestDTO.setTitle("제목post");
        requestDTO.setContent(testString);
        String requestBody = om.writeValueAsString(requestDTO);

        System.out.println("테스트 : "+requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                post("/mentorings")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        resultActions.andExpect(jsonPath("$.status").value("fail"));
    }

    @Test
    @WithUserDetails("test7@example.com")
    @DisplayName("멘토가 게시글 생성할때 내용 300자 이상테스트")
    void MentoringPostSuccess300Test() throws Exception {
        String testString = "LLLorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla facilisi. Sed non augue eget metus suscipit semper. Vestibulum id mi nec sapienLorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla facilisi. Sed non augue eget metus suscipit semper. Vestibulum id mi nec sapienmi nec sapiegg";
        //300자
        // requestDTO : title, content
        MentorPostRequest.CreateMentorPostDTO requestDTO = new MentorPostRequest.CreateMentorPostDTO();
        requestDTO.setTitle("제목post");
        requestDTO.setContent(testString);
        String requestBody = om.writeValueAsString(requestDTO);

        System.out.println("테스트 : "+requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                post("/mentorings")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        resultActions.andExpect(jsonPath("$.status").value("success"));
    }

    @Test
    @WithUserDetails("test7@example.com")
    @DisplayName("멘토가 게시글 생성할때 제목 299자 테스트")
    void MentoringPostSuccess299Test() throws Exception {
        // requestDTO : title, content
        String testString = "LLorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla facilisi. Sed non augue eget metus suscipit semper. Vestibulum id mi nec sapienLorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla facilisi. Sed non augue eget metus suscipit semper. Vestibulum id mi nec sapienmi nec sapiegg";
        MentorPostRequest.CreateMentorPostDTO requestDTO = new MentorPostRequest.CreateMentorPostDTO();
        requestDTO.setTitle("제목post");
        requestDTO.setContent(testString);

        String requestBody = om.writeValueAsString(requestDTO);

        System.out.println("테스트 : "+requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                post("/mentorings")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        resultActions.andExpect(jsonPath("$.status").value("success"));
    }

    @Test
    @WithUserDetails("test1@example.com")
    @DisplayName("멘토가 게시글 생성할때 제목 null 테스트")
    void MentoringPostTitleNullTest() throws Exception {
        // requestDTO : title, content
        String testString = "LLorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla facilisi. Sed non augue eget metus suscipit semper. Vestibulum id mi nec sapienLorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla facilisi. Sed non augue eget metus suscipit semper. Vestibulum id mi nec sapienmi nec sapiegg";
        MentorPostRequest.CreateMentorPostDTO requestDTO = new MentorPostRequest.CreateMentorPostDTO();
        requestDTO.setContent(testString);

        String requestBody = om.writeValueAsString(requestDTO);

        System.out.println("테스트 : "+requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                post("/mentorings")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        resultActions.andExpect(jsonPath("$.status").value("fail"));
    }

}
