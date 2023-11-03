package com.example.demo.video;

import com.example.demo.config.errors.exception.Exception404;
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

    public List<VideoResponse.VideoResponseDTO> findAllVideo(Integer page) {
        Pageable pageable = PageRequest.of(page,4);

        Page<Video> pageContent = videoJPARepository.findAll(pageable);

        if(pageContent.getTotalPages() == 0){
            throw new Exception404("해당 영상들이 존재하지 않습니다");
        }

        // 각 Video에대해서 Interest, Subtitle 끌어오기
        List<VideoResponse.VideoResponseDTO> videoDTOList = pageContent.getContent().stream().map(
                video -> {
                    List<VideoInterest> videoInterests = videoInterestJPARepository.findVideoInterestByVideoId(video.getId());
                    List<Subtitle> subtitle = subtitleJPARepository.findSubtitleByVideoId(video.getId());

                    return new VideoResponse.VideoResponseDTO(video, videoInterests, subtitle);
                }
        ).collect(Collectors.toList());
        return videoDTOList;
    }
}
