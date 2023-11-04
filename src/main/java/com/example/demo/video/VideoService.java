package com.example.demo.video;

import com.example.demo.config.errors.exception.Exception404;
import com.example.demo.user.userInterest.UserInterest;
import com.example.demo.user.userInterest.UserInterestJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<VideoResponse.VideoAllResponseDTO> findAllVideo(Integer page) {
        Pageable pageable = PageRequest.of(page,4);

        Page<Video> pageContent = videoJPARepository.findAll(pageable);

        if(pageContent.getTotalPages() == 0){
            throw new Exception404("해당 영상들이 존재하지 않습니다");
        }

        // 각 Video에대해서 Interest 끌어오기
        List<VideoResponse.VideoAllResponseDTO> videoDTOList = pageContent.getContent().stream().map(
                video -> {
                    List<VideoInterest> videoInterests = videoInterestJPARepository.findVideoInterestByVideoId(video.getId());

                    return new VideoResponse.VideoAllResponseDTO(video, videoInterests);
                }
        ).collect(Collectors.toList());
        return videoDTOList;
    }

    public VideoResponse.VideoResponseDTO findVideo(int id) {
        Video video = videoJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("해당 영상이 존재하지 않습니다.\n" + "id : " + id));;

        List<VideoInterest> videoInterests = videoInterestJPARepository.findVideoInterestByVideoId(video.getId());
        List<Subtitle> videoSubtitles = subtitleJPARepository.findSubtitleByVideoId(video.getId());

        VideoResponse.VideoResponseDTO videoResponseDTO = new VideoResponse.VideoResponseDTO(video, videoInterests, videoSubtitles);

        return videoResponseDTO;
    }

//    public List<VideoResponse.VideoAllResponseDTO> findUserCategory(int id) {
//        List<UserInterest> userInterests = userInterestJPARepository.findAllById(id);
//        Pageable pageable = PageRequest.of(0,4);
//        Page<Video> pageContent;
//        if(userInterests.size() == 0)
//        {
//            pageContent = videoJPARepository.findAll(pageable);
//        }
//    }
}
