package com.example.demo.video;

import com.example.demo.config.utils.ApiUtils;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "Video API")
@RequiredArgsConstructor
@RestController
public class VideoRestController {

    private final VideoService videoService;

    @GetMapping("/videos/main")
    public ResponseEntity<?> getUserCategoryVideo(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        List<VideoResponse.VideoAllResponseDTO> responseDTOs = videoService.findAllVideo(page);
        return ResponseEntity.ok(ApiUtils.success(responseDTOs));
    }

    @GetMapping("/videos/{id}")
    public ResponseEntity<?> getMentorPostId(@PathVariable int id) {
        VideoResponse.VideoResponseDTO responseDTO = videoService.findVideo(id);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
