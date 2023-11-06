package com.example.demo;

import com.example.demo.config.errors.exception.Exception404;
import com.example.demo.interest.Interest;
import com.example.demo.interest.InterestJPARepository;
import com.example.demo.mentoring.MentorPost;
import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.user.UserJPARepository;
import com.example.demo.user.userInterest.UserInterest;
import com.example.demo.user.userInterest.UserInterestJPARepository;
import com.example.demo.video.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
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
    @Order(1)
    void save() {

        User user = User.builder()
                .email("anjdal64@gmail.com")
                .password("asdf1234!")
                .firstName("Jin")
                .lastName("Seung")
                .country("Korea")
                .age(21)
                .role(Role.MENTOR)
                .phone("010-0000-0000")
                .build();

        Interest interest1 = Interest.builder()
                .category("test_Interest_1")
                .build();

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

        Subtitle subtitle1 = Subtitle.builder()
                .video(video1)
                .korStartTime("1")
                .korEndTime("2")
                .korSubtitleContent("asdfasdf")
                .engStartTime("12")
                .engEndTime("12")
                .engSubtitleContent("ffff")
                .build();

        Subtitle subtitle2 = Subtitle.builder()
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
        subtitleJPARepository.save(subtitle1);
        subtitleJPARepository.save(subtitle2);
        userJPARepository.save(user);
        videoHistoryJPARepository.save(videoHistory1);
        videoHistoryJPARepository.save(videoHistory2);
        videoHistoryJPARepository.save(videoHistory3);
        userInterestJPARepository.save(userInterest1);
        userInterestJPARepository.save(userInterest2);
    }

    @Test
    @Order(2)
    void findAllTest() throws Exception{
        List<VideoResponse.VideoPageResponseDTO> videoFind = videoService.findAllVideo(0);
    }

    @Test
    @Order(2)
    void HistoryTest() throws Exception{
        User user2 = User.builder()
                .email("anjfffffdal64@gmail.com")
                .password("asdf1234!")
                .firstName("Jin")
                .lastName("Seung")
                .country("Korea")
                .age(21)
                .role(Role.MENTOR)
                .phone("010-0000-0000")
                .build();

        userJPARepository.save(user2);
        VideoResponse.VideoResponseDTO videoFind = videoService.findVideo(3,user2);
    }

    @Test
    @Order(2)
    void findHistoryTest() throws Exception{
        List<VideoResponse.VideoAllResponseDTO> videoFind = videoService.findHistoryVideo(0,1);
        org.assertj.core.api.Assertions.assertThat(3)
                .isEqualTo(videoFind.get(0).getVideoID());
    }

    @Test
    @Order(2)
    void findUserCategoryTest() throws Exception{
        List<VideoResponse.VideoAllResponseDTO> findUserCategory = videoService.findUserCategory(1);
    }

    @Test
    @Order(3)
    void findOmTest() throws Exception {
        List<VideoResponse.VideoPageResponseDTO> videoFind = videoService.findAllVideo(0);

        String responseBody = om.writeValueAsString(videoFind);

        System.out.println("전체조회테스트 : " + responseBody);
    }
}
