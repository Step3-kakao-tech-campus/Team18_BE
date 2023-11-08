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

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql("classpath:db/teardown.sql")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
                get("/videos/main").param("category", String.valueOf(1))
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("video main 전체조회 Category 1 테스트 : "+responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.status").value("success"));
    }


    @Test
    @WithUserDetails("test1@example.com")
    @Order(3)
    void findOmTest() throws Exception {
        List<VideoResponse.VideoPageResponseDTO> videoFind = videoService.findAllVideo(0);

        String responseBody = om.writeValueAsString(videoFind);

        System.out.println("전체조회테스트 : " + responseBody);
    }
}
