package com.example.demo.video.service;

import com.example.demo.config.errors.exception.Exception401;
import com.example.demo.config.errors.exception.Exception404;
import com.example.demo.config.security.CustomUserDetails;
import com.example.demo.user.domain.Role;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserInterest;
import com.example.demo.user.repository.UserInterestJPARepository;
import com.example.demo.video.domain.VideoSubtitle;
import com.example.demo.video.domain.Video;
import com.example.demo.video.domain.VideoHistory;
import com.example.demo.video.domain.VideoInterest;
import com.example.demo.video.dto.VideoRequest;
import com.example.demo.video.dto.VideoResponse;
import com.example.demo.video.repository.SubtitleJPARepository;
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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class VideoService {
    private final VideoJPARepository videoJPARepository;
    private final VideoInterestJPARepository videoInterestJPARepository;
    private final SubtitleJPARepository subtitleJPARepository;
    private final UserInterestJPARepository userInterestJPARepository;
    private final VideoHistoryJPARepository videoHistoryJPARepository;

    private final int MAINVIDEOTOTAL = 100;
    private final int MAINVIDEONUM = 4;
    private final int HISTORYVIDEONUM = 5;

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

    public List<VideoResponse.VideoPageResponseDTO> findAllVideo(String category) {
        Pageable pageable = PageRequest.of(0 ,MAINVIDEOTOTAL);

        Page<Video> videoPage;
        if (category == null) {
            videoPage = videoJPARepository.findAll(pageable);
        } else {
            videoPage = videoInterestJPARepository.findByCategory(category, pageable);
        }

        if (videoPage.getTotalPages() == 0) {
            throw new Exception404("해당 영상들이 존재하지 않습니다");
        }

        // 각 Video에대해서 Interest 끌어오기
        List<VideoResponse.VideoAllResponseDTO> videoDTOList = videoPage.getContent().stream().map(
                video -> {
                    VideoInterest videoInterests = videoInterestJPARepository.findVideoInterestByVideoId(video.getId());
                    return new VideoResponse.VideoAllResponseDTO(video, videoInterests);
                }
        ).collect(Collectors.toList());

        Collections.shuffle(videoDTOList);

        List<VideoResponse.VideoPageResponseDTO> videoPageResponseDTOS = new ArrayList<>();
        List<VideoResponse.VideoAllResponseDTO> tempGroup = new ArrayList<>();
        int page = 0;
        boolean finish = false;
        for(int i = 0;i < videoDTOList.size();i++)
        {
            tempGroup.add(videoDTOList.get(i));
            if ((i + 1) % MAINVIDEONUM == 0 || i == videoDTOList.size() - 1) {
                if(i == videoDTOList.size() - 1)
                    finish = true;
                VideoResponse.VideoPageResponseDTO videoPageResponseDTO = new VideoResponse.VideoPageResponseDTO(
                        page, tempGroup, finish
                );
                tempGroup = new ArrayList<>();
                videoPageResponseDTOS.add(videoPageResponseDTO);
                page++;
            }
        }

        return videoPageResponseDTOS;
    }

    public VideoResponse.VideoResponseDTO findVideo(int id, CustomUserDetails userDetails) {
        Video video = videoJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("해당 영상이 존재하지 않습니다.\n" + "id : " + id));

        video.updateViewCount();
        if (userDetails != null) {
            VideoHistory videoHistory = VideoHistory.builder()
                    .user(userDetails.getUser())
                    .video(video)
                    .build();
            videoHistoryJPARepository.save(videoHistory);
        }

        VideoInterest videoInterest = videoInterestJPARepository.findVideoInterestByVideoId(video.getId());
        List<VideoSubtitle> videoVideoSubtitles = subtitleJPARepository.findSubtitleByVideoId(video.getId());

        List<Video> recommendVideo = videoJPARepository.findThreeRandomVideo(id);
        List<VideoInterest> recommendVideoInterest = recommendVideo.stream()
                .map(rv -> videoInterestJPARepository.findVideoInterestByVideoId(rv.getId()))
                .collect(Collectors.toList());

        VideoResponse.VideoResponseDTO videoResponseDTO = new VideoResponse.VideoResponseDTO(video, videoInterest, videoVideoSubtitles,recommendVideo,recommendVideoInterest);

        return videoResponseDTO;
    }

    public List<VideoResponse.VideoAllResponseDTO> findHistoryVideo(Integer page, int id) {
        Pageable pageable = PageRequest.of(page,HISTORYVIDEONUM);

        Page<VideoHistory> pageContent = videoHistoryJPARepository.findHistoryVideo(id, pageable);

        if(pageContent.getTotalPages() == 0){
            throw new Exception404("해당 영상들이 존재하지 않습니다");
        }

        // 각 Video에대해서 Interest 끌어오기
        List<VideoResponse.VideoAllResponseDTO> videoDTOList = pageContent.getContent().stream().map(
                video -> {
                    VideoInterest videoInterest = videoInterestJPARepository.findVideoInterestByVideoId(video.getVideo().getId());

                    return new VideoResponse.VideoAllResponseDTO(video.getVideo(), videoInterest);
                }
        ).collect(Collectors.toList());
        return videoDTOList;
    }

    public List<VideoResponse.VideoAllResponseDTO> findUserCategory(int id) {
        List<UserInterest> userInterests = userInterestJPARepository.findAllById(id);
        List<String> Interests = userInterests.stream()
                .map(userInterest -> userInterest.getInterest().getCategory()).collect(Collectors.toList());
        Pageable pageable = PageRequest.of(0,4);
        Page<Video> pageContent;
        if(userInterests.size() == 0)
        {
            pageContent = videoJPARepository.findAll(pageable);
        }
        else
        {
            pageContent = videoJPARepository.findByVideoCategory(Interests ,pageable);
        }

        if(pageContent.getTotalPages() == 0){
            throw new Exception404("해당 영상들이 존재하지 않습니다");
        }

        // 각 Video에대해서 Interest 끌어오기
        List<VideoResponse.VideoAllResponseDTO> videoDTOList = pageContent.getContent().stream().map(
                video -> {
                    VideoInterest videoInterests = videoInterestJPARepository.findVideoInterestByVideoId(video.getId());
                    return new VideoResponse.VideoAllResponseDTO(video, videoInterests);
                }
        ).collect(Collectors.toList());
        return videoDTOList;
    }
}
