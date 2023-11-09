package com.example.demo;

import com.example.demo.config.auth.CustomUserDetails;
import com.example.demo.interest.Interest;
import com.example.demo.interest.InterestJPARepository;
<<<<<<< HEAD
import com.example.demo.user.domain.Role;
import com.example.demo.user.domain.User;
import com.example.demo.user.repository.UserJPARepository;
import com.example.demo.user.domain.UserInterest;
import com.example.demo.user.repository.UserInterestJPARepository;
import com.example.demo.video.domain.VideoSubtitle;
import com.example.demo.video.domain.Video;
import com.example.demo.video.domain.VideoHistory;
import com.example.demo.video.domain.VideoInterest;
import com.example.demo.video.dto.VideoRequest;
import com.example.demo.video.repository.VideoSubtitleJPARepository;
import com.example.demo.video.repository.VideoHistoryJPARepository;
import com.example.demo.video.repository.VideoInterestJPARepository;
import com.example.demo.video.repository.VideoJPARepository;
import com.example.demo.video.service.VideoService;
=======
import com.example.demo.mentoring.MentorPostRequest;
import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.user.UserJPARepository;
import com.example.demo.user.userInterest.UserInterest;
import com.example.demo.user.userInterest.UserInterestJPARepository;
>>>>>>> 1fc6cdd0c8cccffc40ec26b1ac967c45ff9e3fe4
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
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
    private VideoSubtitleJPARepository videoSubtitleJPARepository;
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

<<<<<<< HEAD
        Interest interest2 = Interest.builder()
                .category("test_Interest_2")
                .build();

        Interest interest3 = Interest.builder()
                .category("test_Interest_3")
                .build();

        Video video1 = Video.builder()
                .videoUrl("https://www.youtube.com/watch?v=6lw4Cbk1IzA")
                .views(25)
                .videoTitleEng("First page First Video")
                .videoTitleKorean("첫번째 비디오")
                .videoStartTime("23")
                .videoEndTime("100")
                .videoThumbnailUrl( "https://search.pstatic.net/common/?src=http%3A%2F%2Fimgnews.naver.net%2Fimage%2F016%2F2023%2F08%2F10%2F20230810000405_0_20230810112103061.jpg&type=sc960_832")
                .build();

        Video video2 = Video.builder()
                .videoUrl("https://www.youtube.com/watch?v=6lw4Cbk1IzA")
                .views(25)
                .videoTitleEng("Second page Second Video")
                .videoTitleKorean("두번째 비디오")
                .videoStartTime("23")
                .videoEndTime("100")
                .videoThumbnailUrl( "https://search.pstatic.net/common/?src=http%3A%2F%2Fimgnews.naver.net%2Fimage%2F016%2F2023%2F08%2F10%2F20230810000405_0_20230810112103061.jpg&type=sc960_832")
                .build();

        Video video3 = Video.builder()
                .videoUrl("https://www.youtube.com/watch?v=6lw4Cbk1IzA")
                .views(25)
                .videoTitleEng("Third page Third Video")
                .videoTitleKorean("세번쨰 비디오")
                .videoStartTime("23")
                .videoEndTime("100")
                .videoThumbnailUrl( "https://search.pstatic.net/common/?src=http%3A%2F%2Fimgnews.naver.net%2Fimage%2F016%2F2023%2F08%2F10%2F20230810000405_0_20230810112103061.jpg&type=sc960_832")
                .build();

        Video video4 = Video.builder()
                .videoUrl("https://www.youtube.com/watch?v=6lw4Cbk1IzA")
                .views(25)
                .videoTitleEng("Fourth page Fourth Video")
                .videoTitleKorean("네번째 비디오")
                .videoStartTime("23")
                .videoEndTime("100")
                .videoThumbnailUrl( "https://search.pstatic.net/common/?src=http%3A%2F%2Fimgnews.naver.net%2Fimage%2F016%2F2023%2F08%2F10%2F20230810000405_0_20230810112103061.jpg&type=sc960_832")
                .build();

        Video video5 = Video.builder()
                .videoUrl("https://www.youtube.com/watch?v=6lw4Cbk1IzA")
                .views(25)
                .videoTitleEng("Fifth page Fifth Video")
                .videoTitleKorean("다섯번쨰 비디오")
                .videoStartTime("23")
                .videoEndTime("100")
                .videoThumbnailUrl( "https://search.pstatic.net/common/?src=http%3A%2F%2Fimgnews.naver.net%2Fimage%2F016%2F2023%2F08%2F10%2F20230810000405_0_20230810112103061.jpg&type=sc960_832")
                .build();

        Video video6 = Video.builder()
                .videoUrl("https://www.youtube.com/watch?v=6lw4Cbk1IzA")
                .views(25)
                .videoTitleEng("Sixth page Sixth Video")
                .videoTitleKorean("여섯번째 비디오")
                .videoStartTime("23")
                .videoEndTime("100")
                .videoThumbnailUrl( "https://search.pstatic.net/common/?src=http%3A%2F%2Fimgnews.naver.net%2Fimage%2F016%2F2023%2F08%2F10%2F20230810000405_0_20230810112103061.jpg&type=sc960_832")
                .build();

        Video video7 = Video.builder()
                .videoUrl("https://www.youtube.com/watch?v=6lw4Cbk1IzA")
                .views(25)
                .videoTitleEng("Sixth page Sixth Video")
                .videoTitleKorean("여섯번째 비디오")
                .videoStartTime("23")
                .videoEndTime("100")
                .videoThumbnailUrl( "https://search.pstatic.net/common/?src=http%3A%2F%2Fimgnews.naver.net%2Fimage%2F016%2F2023%2F08%2F10%2F20230810000405_0_20230810112103061.jpg&type=sc960_832")
                .build();

        Video video8 = Video.builder()
                .videoUrl("https://www.youtube.com/watch?v=6lw4Cbk1IzA")
                .views(25)
                .videoTitleEng("Sixth page Sixth Video")
                .videoTitleKorean("여섯번째 비디오")
                .videoStartTime("23")
                .videoEndTime("100")
                .videoThumbnailUrl( "https://search.pstatic.net/common/?src=http%3A%2F%2Fimgnews.naver.net%2Fimage%2F016%2F2023%2F08%2F10%2F20230810000405_0_20230810112103061.jpg&type=sc960_832")
                .build();

        Video video9 = Video.builder()
                .videoUrl("https://www.youtube.com/watch?v=6lw4Cbk1IzA")
                .views(25)
                .videoTitleEng("Sixth page Sixth Video")
                .videoTitleKorean("여섯번째 비디오")
                .videoStartTime("23")
                .videoEndTime("100")
                .videoThumbnailUrl( "https://search.pstatic.net/common/?src=http%3A%2F%2Fimgnews.naver.net%2Fimage%2F016%2F2023%2F08%2F10%2F20230810000405_0_20230810112103061.jpg&type=sc960_832")
                .build();

        VideoInterest video1Interest1 = VideoInterest.builder()
                .video(video1)
                .interest(interest1)
                .build();

        VideoInterest video2Interest1 = VideoInterest.builder()
                .video(video2)
                .interest(interest1)
                .build();

        VideoInterest video3Interest1 = VideoInterest.builder()
                .video(video3)
                .interest(interest1)
                .build();

        VideoInterest video4Interest2 = VideoInterest.builder()
                .video(video4)
                .interest(interest2)
                .build();

        VideoInterest video5Interest1 = VideoInterest.builder()
                .video(video5)
                .interest(interest1)
                .build();

        VideoInterest video6Interest2 = VideoInterest.builder()
                .video(video6)
                .interest(interest2)
                .build();

        VideoInterest video7Interest3 = VideoInterest.builder()
                .video(video7)
                .interest(interest3)
                .build();

        VideoInterest video8Interest3 = VideoInterest.builder()
                .video(video8)
                .interest(interest3)
                .build();

        VideoInterest video9Interest1 = VideoInterest.builder()
                .video(video9)
                .interest(interest1)
                .build();

        VideoSubtitle videoSubtitle1 = VideoSubtitle.builder()
                .video(video1)
                .korStartTime("1")
                .korEndTime("2")
                .korSubtitleContent("asdfasdf")
                .engStartTime("12")
                .engEndTime("12")
                .engSubtitleContent("ffff")
                .build();

        VideoSubtitle videoSubtitle2 = VideoSubtitle.builder()
                .video(video1)
                .korStartTime("4")
                .korEndTime("7")
                .korSubtitleContent("aaaaasdfasdf")
                .engStartTime("12")
                .engEndTime("12")
                .engSubtitleContent("ffff")
                .build();

        VideoHistory videoHistory1 = VideoHistory.builder()
                .video(video1)
                .user(user)
                .build();

        VideoHistory videoHistory2 = VideoHistory.builder()
                .video(video5)
                .user(user)
                .build();

        VideoHistory videoHistory3 = VideoHistory.builder()
                .video(video3)
                .user(user)
                .build();

        UserInterest userInterest1 = UserInterest.builder()
                .user(user)
                .interest(interest1)
                .build();

        UserInterest userInterest2 = UserInterest.builder()
                .user(user)
                .interest(interest3)
                .build();

        interestJPARepository.save(interest1);
        interestJPARepository.save(interest2);
        interestJPARepository.save(interest3);
        videoJPARepository.save(video1);
        videoJPARepository.save(video2);
        videoJPARepository.save(video3);
        videoJPARepository.save(video4);
        videoJPARepository.save(video5);
        videoJPARepository.save(video6);
        videoJPARepository.save(video7);
        videoJPARepository.save(video8);
        videoJPARepository.save(video9);
        videoInterestJPARepository.save(video1Interest1);
        videoInterestJPARepository.save(video2Interest1);
        videoInterestJPARepository.save(video3Interest1);
        videoInterestJPARepository.save(video4Interest2);
        videoInterestJPARepository.save(video5Interest1);
        videoInterestJPARepository.save(video6Interest2);
        videoInterestJPARepository.save(video7Interest3);
        videoInterestJPARepository.save(video8Interest3);
        videoInterestJPARepository.save(video9Interest1);
        videoSubtitleJPARepository.save(videoSubtitle1);
        videoSubtitleJPARepository.save(videoSubtitle2);
        userJPARepository.save(user);
        videoHistoryJPARepository.save(videoHistory1);
        videoHistoryJPARepository.save(videoHistory2);
        videoHistoryJPARepository.save(videoHistory3);
        userInterestJPARepository.save(userInterest1);
        userInterestJPARepository.save(userInterest2);
=======
        // verify
        resultActions.andExpect(jsonPath("$.status").value("success"));
>>>>>>> 1fc6cdd0c8cccffc40ec26b1ac967c45ff9e3fe4
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
