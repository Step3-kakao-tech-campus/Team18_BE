package com.example.demo.video;

import com.example.demo.config.auth.CustomUserDetails;
import com.example.demo.config.utils.ApiResponseBuilder;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Video API")
@RequiredArgsConstructor
@RestController
public class VideoRestController {

    private final VideoService videoService;

   @GetMapping("/videos/interest")
    public ResponseEntity<?> getUserCategoryVideo(@AuthenticationPrincipal CustomUserDetails userDetails){
        List<VideoResponse.VideoAllResponseDTO> responseDTOs = videoService.findUserCategory(userDetails.getUser().getId());
        return ResponseEntity.ok(ApiResponseBuilder.success(responseDTOs));
    }

    @GetMapping("/videos/main")
    public ResponseEntity<?> getCategoryFilterVideo(@RequestParam(value = "category", defaultValue = "0") int id) {
        List<VideoResponse.VideoPageResponseDTO> responseDTOs = videoService.findAllVideo(id);
        return ResponseEntity.ok(ApiResponseBuilder.success(responseDTOs));
    }

    @GetMapping("/videos/{id}")
    public ResponseEntity<?> getVideoId(@PathVariable int id) {
        VideoResponse.VideoResponseDTO responseDTO = videoService.findVideo(id);
        return ResponseEntity.ok(ApiResponseBuilder.success(responseDTO));
    }

    @GetMapping("/videos/history")
    public ResponseEntity<?> getVideoHistory(@RequestParam(value = "page", defaultValue = "0") Integer page, @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<VideoResponse.VideoAllResponseDTO> responseDTO = videoService.findHistoryVideo(page, userDetails.getUser().getId());
        return ResponseEntity.ok(ApiResponseBuilder.success(responseDTO));
    }

    @PostMapping("videos/view/{id}")
    public ResponseEntity<?> postVideoView(@PathVariable int id){
        videoService.addVideoView(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.successWithNoContent());
    }
}
