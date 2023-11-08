package com.example.demo;

import com.example.demo.config.auth.CustomUserDetails;
import com.example.demo.config.errors.exception.Exception404;
import com.example.demo.interest.Interest;
import com.example.demo.interest.InterestJPARepository;
import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.user.UserJPARepository;
import com.example.demo.user.userInterest.UserInterest;
import com.example.demo.user.userInterest.UserInterestJPARepository;
import com.example.demo.video.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@Sql("classpath:db/teardown.sql")
public class VideoTest extends RestDoc{
    @Autowired
    private InterestJPARepository interestJPARepository;
    @Autowired
    private VideoInterestJPARepository videoInterestJPARepository;
    @Autowired
    private VideoJPARepository videoJPARepository;
    @Autowired
    private SubtitleJPARepository subtitleJPARepository;
    @Autowired
    private UserJPARepository userJPARepository;
    @Autowired
    private VideoHistoryJPARepository videoHistoryJPARepository;
    @Autowired
    private UserInterestJPARepository userInterestJPARepository;

    @Autowired
    private VideoService videoService;

    @Autowired
    private ObjectMapper om;

    @Test
    @DisplayName("video main 전체조회 Category Default")
    void findAllVideoCategoryDefaultTest() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(
                get("/videos/main")
                        .param("page", String.valueOf(0))
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("video main 전체조회 테스트 : "+responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.status").value("success"));
    }

    @Test
    @DisplayName("video main 전체조회 Category 1")
    void findAllVideoCategoryTest() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(
                get("/videos/main")
                        .param("page", String.valueOf(0))
                        .param("category", String.valueOf(1))
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("video main 전체조회 Category 1 테스트 : "+responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.status").value("success"));
    }

    @Test
    @DisplayName("video 개인 조회 테스트")
    void findVideoNoAuthTest() throws Exception {
        // given
        int videoId = 1;

        // when
        ResultActions resultActions = mvc.perform(
                get("/videos/" + videoId)
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("video 개인 조회 인증 없이 테스트 : "+responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.status").value("success"));
    }

    @Test
    @WithUserDetails("test1@example.com")
    @DisplayName("video 개인 조회 인증있이 테스트")
    void findVideoAuthTest() throws Exception {
        // given
        int videoId = 1;

        // when
        ResultActions resultActions = mvc.perform(
                get("/videos/" + videoId)
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("video 개인 조회 인증 있이 테스트 : "+responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.status").value("success"));
    }

    @Test
    @WithUserDetails("test1@example.com")
    @DisplayName("video 개인 조회 인증있이 기록 확인 테스트")
    void findHistoryTest() throws Exception {
        // given
        int videoId = 3;

        mvc.perform(
                get("/videos/" + videoId)
        );

        ResultActions resultActions = mvc.perform(
                get("/videos/history")
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("video 개인 조회 인증있이 기록 확인 테스트 : "+responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.status").value("success"));
    }

    @Test
    @WithUserDetails("test1@example.com")
    @DisplayName("video 개인 조회 인증있이 기록 확인 최근 영상 같을 경우 추가 X 테스트")
    void findHistoryExceptionTest() throws Exception {
        // given
        int videoId = 1;

        mvc.perform(
                get("/videos/" + videoId)
        );

        ResultActions resultActions = mvc.perform(
                get("/videos/history")
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("video 개인 조회 인증있이 기록 확인 최근 영상 같을 경우 추가 X 테스트 : "+responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.status").value("success"));
    }

    @Test
    @DisplayName("video 개인 조회 인증있이 기록 확인 테스트 인증이 없으니 error")
    void findHistoryFailTest() throws Exception {
        // given
        int videoId = 1;

        // when
        mvc.perform(get("/videos/" + videoId));

        ResultActions resultActions = mvc.perform(
                get("/videos/history")
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("video 개인 조회 인증있이 기록 확인 테스트 인증이 없으니 error : "+responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.status").value("error"));
    }

    @Test
    @WithUserDetails("test1@example.com")
    @DisplayName("video 전체 조회 관심있는 영상")
    void findIntestVideo() throws Exception {
        // given

        ResultActions resultActions = mvc.perform(
                get("/videos/interest")
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("video 전체 조회 관심있는 영상 : "+responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.status").value("success"));
    }

}
