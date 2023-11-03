package com.example.demo;

import com.example.demo.interest.Interest;
import com.example.demo.interest.InterestJPARepository;
import com.example.demo.video.*;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private VideoService videoService;

    @Autowired
    private ObjectMapper om;

    @Test
    @Order(1)
    void findAllTest() {

        Interest interest1 = Interest.builder()
                .category("test_Interest_1")
                .build();

        Interest interest2 = Interest.builder()
                .category("test_Interest_2")
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

        VideoInterest video1Interest1 = VideoInterest.builder()
                .video(video1)
                .interest(interest1)
                .build();

        VideoInterest video1Interest2 = VideoInterest.builder()
                .video(video1)
                .interest(interest2)
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


        interestJPARepository.save(interest1);
        interestJPARepository.save(interest2);
        videoJPARepository.save(video1);
        videoJPARepository.save(video2);
        videoJPARepository.save(video3);
        videoJPARepository.save(video4);
        videoJPARepository.save(video5);
        videoJPARepository.save(video6);
        videoInterestJPARepository.save(video1Interest1);
        videoInterestJPARepository.save(video1Interest2);
        videoInterestJPARepository.save(video2Interest1);
        videoInterestJPARepository.save(video3Interest1);
        videoInterestJPARepository.save(video4Interest2);
        videoInterestJPARepository.save(video5Interest1);
        videoInterestJPARepository.save(video6Interest2);
        subtitleJPARepository.save(subtitle1);
        subtitleJPARepository.save(subtitle2);
    }

    @Test
    @Order(2)
    void findAllServiceTest() throws Exception {
        VideoResponse.VideoResponseDTO videoFind = videoService.findVideo(1);

        String responseBody = om.writeValueAsString(videoFind);

        System.out.println("전체조회테스트 : " + responseBody);
    }
}
