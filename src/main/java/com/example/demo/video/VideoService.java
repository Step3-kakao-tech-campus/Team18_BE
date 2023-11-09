package com.example.demo.video;

import com.example.demo.config.auth.CustomUserDetails;
import com.example.demo.config.errors.exception.Exception401;
import com.example.demo.config.errors.exception.Exception404;
import com.example.demo.interest.Interest;
import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.user.userInterest.UserInterest;
import com.example.demo.user.userInterest.UserInterestJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Transactional
@RequiredArgsConstructor
@Service
public class VideoService {
    private final VideoJPARepository videoJPARepository;
    private final VideoInterestJPARepository videoInterestJPARepository;
    private final SubtitleJPARepository subtitleJPARepository;
    private final UserInterestJPARepository userInterestJPARepository;
    private final VideoHistoryJPARepository videoHistoryJPARepository;

    private final int MAINVIDEOTOTAL = 16;
    private final int MAINVIDEONUM = 4;
    private final int HISTORYVIDEONUM = 5;

    public VideoResponse.VideoPageResponseDTO findAllVideo(int page, int categoryId) {
        Pageable pageable = PageRequest.of(page ,MAINVIDEOTOTAL);

        Page<Video> pageContent = getPageContentByCategoryId(categoryId, pageable);

        // 각 Video에대해서 Interest 끌어오기
        List<VideoResponse.VideoAllResponseDTO> videoDTOList = pageContent.getContent().stream().map(
                video -> {
                    VideoInterest videoInterests = videoInterestJPARepository.findVideoInterestByVideoId(video.getId());
                    return new VideoResponse.VideoAllResponseDTO(video, videoInterests);
                }
        ).collect(Collectors.toList());

        //랜덤으로 섞기
        Collections.shuffle(videoDTOList);

        boolean isLastPage = pageContent.getNumberOfElements() < MAINVIDEOTOTAL;
        List<VideoResponse.VideoAllResponseDTO> contents = videoDTOList.stream()
                .limit(MAINVIDEONUM)
                .collect(Collectors.toList());
        VideoResponse.VideoPageResponseDTO videoPageResponseDTOs = new VideoResponse.VideoPageResponseDTO(page, contents, isLastPage);

        return videoPageResponseDTOs;
    }

    public Page<Video> getPageContentByCategoryId(int categoryId, Pageable pageable) throws Exception404 {
        Page<Video> pageContent = (categoryId == 0) ?
                videoJPARepository.findAll(pageable) :
                videoJPARepository.findByCategoryId(categoryId, pageable);
        return pageContent;
    }


    public VideoResponse.VideoResponseDTO findVideo(int id, CustomUserDetails customUserDetails) {

        User user = Optional.ofNullable(customUserDetails)
                .map(CustomUserDetails::getUser)
                .orElse(null);

        Video video = videoJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("해당 영상이 존재하지 않습니다.\n" + "id : " + id));

        video.addView();

        if(user != null)
        {
            //최근 기록이 같은 영상일 경우 추가해서는 안됨.
            Page<VideoHistory> recentVideos = videoHistoryJPARepository.findHistoryVideo(user.getId(), PageRequest.of(0, 1));
            if (isNewVideoHistory(recentVideos, video)) {
                VideoHistory videoHistory = new VideoHistory(user, video);
                videoHistoryJPARepository.save(videoHistory);
            }
        }

        VideoInterest videoInterest = videoInterestJPARepository.findVideoInterestByVideoId(video.getId());
        List<Subtitle> videoSubtitles = subtitleJPARepository.findSubtitleByVideoId(video.getId());

        List<Video> recommendVideo = videoJPARepository.findThreeRandomVideo(id);
        List<VideoInterest> recommendVideoInterest = recommendVideo.stream()
                .map(rv -> videoInterestJPARepository.findVideoInterestByVideoId(rv.getId()))
                .collect(Collectors.toList());

        return new VideoResponse.VideoResponseDTO(video, videoInterest, videoSubtitles,recommendVideo,recommendVideoInterest);
    }

    private boolean isNewVideoHistory(Page<VideoHistory> recentVideos, Video currentVideo) {
        return recentVideos != null &&
                !recentVideos.getContent().isEmpty() &&
                recentVideos.getContent().get(0).getVideo().getId() != currentVideo.getId();
    }

    public List<VideoResponse.VideoAllResponseDTO> findHistoryVideo(Integer page, int id) {
        Pageable pageable = PageRequest.of(page,HISTORYVIDEONUM);

        Page<VideoHistory> pageContent = videoHistoryJPARepository.findHistoryVideo(id, pageable);

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

    //백오피스용 관리자 영상 올리기
    public void createVideo(VideoRequest.CreateDTO createDTO, User user) {
        if ( user.getRole() != Role.ADMIN ) {
            throw new Exception401("관리자만 가능합니다.");
        }

        Video video = Video.builder()
                .videoUrl(createDTO.getVideoUrl())
                .videoEndTime(createDTO.getVideoEndTime())
                .videoStartTime(createDTO.getVideoStartTime())
                .videoThumbnailUrl(createDTO.getVideoThumbnailUrl())
                .videoTitleEng(createDTO.getVideoTitleEng())
                .videoTitleKorean(createDTO.getVideoTitleKorean())
                .build();

        VideoInterest videoInterest = VideoInterest.builder()
                .video(video)
                .interest(createDTO.getVideoInterest())
                .build();

        videoJPARepository.save(video);
        videoInterestJPARepository.save(videoInterest);

        for (VideoRequest.CreateDTO.SubtitleCreateDTO subtitleDTO : createDTO.getSubtitleCreateDTOList()) {
            Subtitle subtitle = Subtitle.builder()
                    .video(video)
                    .korStartTime(subtitleDTO.getKorStartTime())
                    .korEndTime(subtitleDTO.getKorEndTime())
                    .korSubtitleContent(subtitleDTO.getKorSubtitleContent())
                    .engStartTime(subtitleDTO.getEngStartTime())
                    .engEndTime(subtitleDTO.getEngEndTime())
                    .engSubtitleContent(subtitleDTO.getEngSubtitleContent())
                    .build();
            subtitleJPARepository.save(subtitle);
        }
    }
}
