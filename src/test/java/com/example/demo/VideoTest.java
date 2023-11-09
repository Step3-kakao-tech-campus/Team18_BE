package com.example.demo;

import com.example.demo.config.auth.CustomUserDetails;
import com.example.demo.config.errors.exception.Exception404;
import com.example.demo.interest.Interest;
import com.example.demo.interest.InterestJPARepository;
import com.example.demo.mentoring.MentorPostRequest;
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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
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
    @WithUserDetails("test5@example.com")
    @DisplayName("video 개인 조회 인증있이 기록 확인 최근 영상 없을시 테스트")
    void findHistoryNoHistoryExceptionTest() throws Exception {
        // given

        ResultActions resultActions = mvc.perform(
                get("/videos/history")
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("video 개인 조회 인증있이 기록 확인 최근 영상 없을시 테스트 : "+responseBody);

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

    @Test
    @DisplayName("post Unit Test")
    void postVideoUnitTest() throws Exception{
        User user3 = User.builder()
                .email("anjfffffffffdal64@gmail.com")
                .password("asdf1234!")
                .firstName("Jin")
                .lastName("Seung")
                .country("Korea")
                .birthDate(LocalDate.now())
                .role(Role.ADMIN)
                .phone("010-0000-0000")
                .build();

        Interest interest4 = Interest.builder()
                .category("test_Interest_4")
                .build();

        interestJPARepository.save(interest4);

        VideoRequest.CreateDTO createDTO = getCreateDTO();
        createDTO.setVideoInterest(interest4);

        videoService.createVideo(createDTO, user3);
    }

    private static VideoRequest.CreateDTO getCreateDTO() {
        VideoRequest.CreateDTO createDTO = new VideoRequest.CreateDTO();
        List<VideoRequest.CreateDTO.SubtitleCreateDTO> subtitleCreateDTOs = getSubtitleCreateDTOS();

        createDTO.setVideoUrl("www.naver.com");
        createDTO.setVideoStartTime("0");
        createDTO.setVideoEndTime("15");
        createDTO.setVideoTitleKorean("네이버");
        createDTO.setVideoTitleEng("naver");
        createDTO.setVideoThumbnailUrl("alsjflsdjfsakl.com");
        createDTO.setSubtitleCreateDTOList(subtitleCreateDTOs);
        return createDTO;
    }

    private static List<VideoRequest.CreateDTO.SubtitleCreateDTO> getSubtitleCreateDTOS() {
        VideoRequest.CreateDTO.SubtitleCreateDTO subDTO1 = new VideoRequest.CreateDTO.SubtitleCreateDTO();
        VideoRequest.CreateDTO.SubtitleCreateDTO subDTO2 = new VideoRequest.CreateDTO.SubtitleCreateDTO();

        subDTO1.setKorStartTime("1");
        subDTO1.setKorEndTime("3");
        subDTO1.setKorSubtitleContent("한글");
        subDTO1.setEngStartTime("1");
        subDTO1.setEngEndTime("4");
        subDTO1.setEngSubtitleContent("sdfasdfsd");

        subDTO2.setKorStartTime("1");
        subDTO2.setKorEndTime("3");
        subDTO2.setKorSubtitleContent("한글");
        subDTO2.setEngStartTime("1");
        subDTO2.setEngEndTime("4");
        subDTO2.setEngSubtitleContent("sdfasdfsd");

        List<VideoRequest.CreateDTO.SubtitleCreateDTO> subtitleCreateDTOs = new ArrayList<>();
        subtitleCreateDTOs.add(subDTO1);
        subtitleCreateDTOs.add(subDTO2);
        return subtitleCreateDTOs;
    }

    @Test
    @WithUserDetails("admin@example.com")
    @DisplayName("postTest")
    void postVideoTest() throws Exception {
        // given

        // requestDTO : title, content
        Interest interest = interestJPARepository.findById(1).get();

        VideoRequest.CreateDTO requestDTO = new VideoRequest.CreateDTO();
        requestDTO.setVideoTitleEng("posttest");
        requestDTO.setVideoUrl("www.naver.com");
        requestDTO.setVideoTitleKorean("한글");
        requestDTO.setVideoStartTime("1");
        requestDTO.setVideoEndTime("12");
        requestDTO.setVideoThumbnailUrl("www.google.com");
        requestDTO.setVideoInterest(interest);
        requestDTO.setSubtitleCreateDTOList(null);

        String requestBody = om.writeValueAsString(requestDTO);

        System.out.println("테스트 : "+requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                post("/videos")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.status").value("error"));
    }

}
