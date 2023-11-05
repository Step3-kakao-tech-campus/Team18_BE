package com.example.demo.video;

import com.example.demo.config.errors.exception.Exception404;
import com.example.demo.interest.Interest;
import com.example.demo.user.userInterest.UserInterest;
import com.example.demo.user.userInterest.UserInterestJPARepository;
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
    private final SubtitleJPARepository subtitleJPARepository;
    private final UserInterestJPARepository userInterestJPARepository;
    private final VideoHistoryJPARepository videoHistoryJPARepository;

    private final int MAINVIDEOTOTAL = 100;
    private final int MAINVIDEONUM = 4;
    private final int HISTORYVIDEONUM = 5;

    public List<VideoResponse.VideoPageResponseDTO> findAllVideo(int categoryId) {
        Pageable pageable = PageRequest.of(0 ,MAINVIDEOTOTAL);

        Page<Video> pageContent;
        if(categoryId == 0)
            pageContent = videoJPARepository.findAll(pageable);
        else
            pageContent = videoJPARepository.findByCategoryId(categoryId, pageable);


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

    public VideoResponse.VideoResponseDTO findVideo(int id) {
        Video video = videoJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("해당 영상이 존재하지 않습니다.\n" + "id : " + id));

        VideoInterest videoInterest = videoInterestJPARepository.findVideoInterestByVideoId(video.getId());
        List<Subtitle> videoSubtitles = subtitleJPARepository.findSubtitleByVideoId(video.getId());

        VideoResponse.VideoResponseDTO videoResponseDTO = new VideoResponse.VideoResponseDTO(video, videoInterest, videoSubtitles);

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

    public void addVideoView(int id) {
        Video video = videoJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("해당 영상이 존재하지 않습니다.\n" + "id : " + id));
        video.addView();
    }
}
