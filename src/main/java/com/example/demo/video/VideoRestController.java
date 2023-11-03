package com.example.demo.video;

import com.example.demo.config.auth.CustomUserDetails;
import com.example.demo.config.utils.ApiUtils;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "Video API")
@RequiredArgsConstructor
@RestController
public class VideoRestController {

    private final VideoService videoService;

    @GetMapping("/videos/main")
    public ResponseEntity<?> getUserCategoryVideo(@RequestParam(value = "page", defaultValue = "0") Integer page, @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<VideoResponse.VideoResponseDTO> responseDTOs = videoService.findAllVideo(page);
        return ResponseEntity.ok(ApiUtils.success(responseDTOs));
    }
}