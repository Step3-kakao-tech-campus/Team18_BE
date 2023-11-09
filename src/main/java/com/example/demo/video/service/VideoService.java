package com.example.demo.video.service;

import com.example.demo.config.auth.CustomUserDetails;
import com.example.demo.config.errors.exception.Exception404;
import com.example.demo.config.security.CustomUserDetails;
import com.example.demo.user.domain.UserInterest;
import com.example.demo.user.repository.UserInterestJPARepository;
import com.example.demo.video.domain.VideoSubtitle;
import com.example.demo.video.domain.Video;
import com.example.demo.video.domain.VideoHistory;
import com.example.demo.video.domain.VideoInterest;
import com.example.demo.video.dto.VideoResponse;
import com.example.demo.video.repository.VideoSubtitleJPARepository;
import com.example.demo.video.repository.VideoHistoryJPARepository;
import com.example.demo.video.repository.VideoInterestJPARepository;
import com.example.demo.video.repository.VideoJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class VideoService {
    private final VideoJPARepository videoJPARepository;
    private final VideoInterestJPARepository videoInterestJPARepository;
    private final VideoSubtitleJPARepository videoSubtitleJPARepository;
    private final UserInterestJPARepository userInterestJPARepository;
    private final VideoHistoryJPARepository videoHistoryJPARepository;

    private final int MAINVIDEOTOTAL = 16;
    private final int MAINVIDEONUM = 4;
    private final int VIDEO_HISTORY_NUM = 5;

<<<<<<< HEAD:src/main/java/com/example/demo/video/service/VideoService.java
//    public void createVideo(VideoRequest.VideoDTO requestDTO, CustomUserDetails userDetails) {
//        if (userDetails.getUser().getRole() != Role.ADMIN ) {
//            throw new Exception401("관리자만 가능합니다.");
//        }
//
//        Video video = Video.builder()
//                .videoUrl(requestDTO.getVideoUrl())
//                .videoTitleKorean(requestDTO.getVideoTitleKorean())
//                .videoTitleEng(requestDTO.getVideoTitleEng())
//                .videoIntroduction(requestDTO.getVideoIntroduction())
//                .videoStartTime(requestDTO.getVideoStartTime())
//                .videoEndTime(requestDTO.getVideoEndTime())
//                .videoThumbnailUrl(requestDTO.getVideoThumbnailUrl())
//                .build();
//
//        VideoInterest videoInterest = VideoInterest.builder()
//                .video(video)
//                .interest(requestDTO.getVideoInterest())
//                .build();
//
//        videoJPARepository.save(video);
//        videoInterestJPARepository.save(videoInterest);
//
//        for (VideoRequest.CreateDTO.SubtitleCreateDTO subtitleDTO : createDTO.getSubtitleCreateDTOList()) {
//            VideoSubtitle videoSubtitle = VideoSubtitle.builder()
//                    .video(video)
//                    .korStartTime(subtitleDTO.getKorStartTime())
//                    .korEndTime(subtitleDTO.getKorEndTime())
//                    .korSubtitleContent(subtitleDTO.getKorSubtitleContent())
//                    .engStartTime(subtitleDTO.getEngStartTime())
//                    .engEndTime(subtitleDTO.getEngEndTime())
//                    .engSubtitleContent(subtitleDTO.getEngSubtitleContent())
//                    .build();
//            subtitleJPARepository.save(videoSubtitle);
//        }
//    }

//    public List<VideoResponse.VideoPageResponseDTO> findAllVideo(String category, int id) {
//        Pageable pageable = PageRequest.of(0 ,MAINVIDEOTOTAL);
//
//        Page<Video> videoPage;
//        if (category == null) {
//            videoPage = videoJPARepository.findAll(pageable);
//        } else {
//            videoPage = videoInterestJPARepository.findByCategory(category, pageable);
//        }
//
//        if (videoPage.getTotalPages() == 0) {
//            throw new Exception404("해당 영상들이 존재하지 않습니다");
//        }
//=======
//    public VideoResponse.VideoPageResponseDTO findAllVideo(int page, int categoryId) {
//        Pageable pageable = PageRequest.of(page ,MAINVIDEOTOTAL);
//
//        Page<Video> pageContent = getPageContentByCategoryId(categoryId, pageable);
//>>>>>>> 1fc6cdd0c8cccffc40ec26b1ac967c45ff9e3fe4:src/main/java/com/example/demo/video/VideoService.java
//
//        // 각 Video에대해서 Interest 끌어오기
//        List<VideoResponse.VideoAllResponseDTO> videoDTOList = videoPage.getContent().stream().map(
//                video -> {
//                    VideoInterest videoInterests = videoInterestJPARepository.findVideoInterestByVideoId(video.getId());
//                    return new VideoResponse.VideoAllResponseDTO(video, videoInterests);
//                }
//        ).collect(Collectors.toList());
//
//        //랜덤으로 섞기
//        Collections.shuffle(videoDTOList);
//
//        boolean isLastPage = pageContent.getNumberOfElements() < MAINVIDEOTOTAL;
//        List<VideoResponse.VideoAllResponseDTO> contents = videoDTOList.stream()
//                .limit(MAINVIDEONUM)
//                .collect(Collectors.toList());
//        VideoResponse.VideoPageResponseDTO videoPageResponseDTOs = new VideoResponse.VideoPageResponseDTO(page, contents, isLastPage);
//
//        return videoPageResponseDTOs;
//    }


//    public VideoResponse.VideoResponseDTO findVideo(int id, CustomUserDetails userDetails) {
//        Video video = videoJPARepository.findById(id)
//                .orElseThrow(() -> new Exception404("해당 영상이 존재하지 않습니다.\n" + "id : " + id));
//
//        video.updateViewCount();
//        if (userDetails != null) {
//            VideoHistory videoHistory = VideoHistory.builder()
//                    .user(userDetails.getUser())
//                    .video(video)
//                    .build();
//            videoHistoryJPARepository.save(videoHistory);
//
//    public Page<Video> getPageContentByCategoryId(int categoryId, Pageable pageable) throws Exception404 {
//        Page<Video> pageContent = (categoryId == 0) ?
//                videoJPARepository.findAll(pageable) :
//                videoJPARepository.findByCategoryId(categoryId, pageable);
//        return pageContent;
//    }


    public VideoResponse.VideoResponseDTO findVideo(int id, CustomUserDetails userDetails) {
        Video video = videoJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("해당 영상이 존재하지 않습니다.\n" + "id : " + id));

        video.updateViewCount();
        if (userDetails != null) {
            Pageable pageable = PageRequest.of(0, 10);
            Page<Video> pageContent = videoHistoryJPARepository.findAllByUserId(userDetails.getUser().getId(), pageable);
            if (!pageContent.getContent().contains(video)) {
                VideoHistory videoHistory = new VideoHistory(userDetails.getUser(), video);
                videoHistoryJPARepository.save(videoHistory);
            }
        }

        List<VideoInterest> videoInterestList = videoInterestJPARepository.findVideoInterestByVideoId(video.getId());
        List<VideoSubtitle> videoSubtitleList = videoSubtitleJPARepository.findSubtitleByVideoId(video.getId());

        List<Video> recommendVideoList = videoJPARepository.findThreeRandomVideo(id);
        List<VideoInterest> recommendVideoInterestList = recommendVideoList.stream()
                .map(rv -> videoInterestJPARepository.findVideoInterestByVideoId(rv.getId()))
                .collect(Collectors.toList());

        VideoResponse.VideoResponseDTO videoResponseDTO = new VideoResponse.VideoResponseDTO(video, videoInterest, videoVideoSubtitles,recommendVideo,recommendVideoInterest);

        return videoResponseDTO;
        return new VideoResponse.VideoResponseDTO(video, videoInterest, videoSubtitles,recommendVideo,recommendVideoInterest);
    }

    public List<VideoResponse.VideoHistoryDTO> findVideoHistoryList(int page, CustomUserDetails userDetails) {
        Pageable pageable = PageRequest.of(page,VIDEO_HISTORY_NUM);
        Page<Video> pageContent = videoHistoryJPARepository.findAllByUserId(userDetails.getUser().getId(), pageable);

        List<VideoResponse.VideoHistoryDTO> responseDTOs = new ArrayList<>();
        for (Video video : pageContent.getContent()) {
            List<VideoInterest> videoInterestList = videoInterestJPARepository.findVideoInterestByVideoId(video.getId());
            VideoResponse.VideoHistoryDTO responseDTO = new VideoResponse.VideoHistoryDTO(video, videoInterestList);
            responseDTOs.add(responseDTO);
        }
        return responseDTOs;
    }

    public List<VideoResponse.VideoAllResponseDTO> findUserCategory(int id) {
        List<UserInterest> userInterests = userInterestJPARepository.findAllById(id);
        List<String> Interests = userInterests.stream()
                .map(userInterest -> userInterest.getInterest().getCategory()).collect(Collectors.toList());
        Pageable pageable = PageRequest.of(0,4);

        Page<Video> pageContent = userInterests.isEmpty()
                ? videoJPARepository.findAll(pageable)
                : videoJPARepository.findByVideoCategory(Interests, pageable);

        // 각 Video에대해서 Interest 끌어오기
        List<VideoResponse.VideoAllResponseDTO> videoDTOList = pageContent.getContent().stream().map(
                video -> {
                    VideoInterest videoInterests = videoInterestJPARepository.findVideoInterestByVideoId(video.getId());
                    return new VideoResponse.VideoAllResponseDTO(video, videoInterests);
                }
        ).collect(Collectors.toList());
        return videoDTOList;
    }



//    //백오피스용 관리자 영상 올리기
//    public void createVideo(VideoRequest.CreateDTO createDTO, User user) {
//        if ( user.getRole() != Role.ADMIN ) {
//            throw new Exception401("관리자만 가능합니다.");
//        }
//
//        Video video = Video.builder()
//                .videoUrl(createDTO.getVideoUrl())
//                .videoEndTime(createDTO.getVideoEndTime())
//                .videoStartTime(createDTO.getVideoStartTime())
//                .videoThumbnailUrl(createDTO.getVideoThumbnailUrl())
//                .videoTitleEng(createDTO.getVideoTitleEng())
//                .videoTitleKorean(createDTO.getVideoTitleKorean())
//                .build();
//
//        VideoInterest videoInterest = VideoInterest.builder()
//                .video(video)
//                .interest(createDTO.getVideoInterest())
//                .build();
//
//        videoJPARepository.save(video);
//        videoInterestJPARepository.save(videoInterest);
//
//        for (VideoRequest.CreateDTO.SubtitleCreateDTO subtitleDTO : createDTO.getSubtitleCreateDTOList()) {
//            Subtitle subtitle = Subtitle.builder()
//                    .video(video)
//                    .korStartTime(subtitleDTO.getKorStartTime())
//                    .korEndTime(subtitleDTO.getKorEndTime())
//                    .korSubtitleContent(subtitleDTO.getKorSubtitleContent())
//                    .engStartTime(subtitleDTO.getEngStartTime())
//                    .engEndTime(subtitleDTO.getEngEndTime())
//                    .engSubtitleContent(subtitleDTO.getEngSubtitleContent())
//                    .build();
//            subtitleJPARepository.save(subtitle);
//        }
//    }

}
