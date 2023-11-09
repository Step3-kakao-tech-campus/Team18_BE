package com.example.demo.video;

import com.example.demo.config.auth.CustomUserDetails;
import com.example.demo.config.utils.ApiResponseBuilder;
import com.example.demo.user.User;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Video API")
@RequiredArgsConstructor
@RestController
public class VideoRestController {

    private final VideoService videoService;

   @GetMapping("/videos/interest")
   @Operation(summary = "유저의 흥미있는 영상 요청")
    public ResponseEntity<?> getUserCategoryVideo(@AuthenticationPrincipal CustomUserDetails userDetails){
        List<VideoResponse.VideoAllResponseDTO> responseDTOs = videoService.findUserCategory(userDetails.getUser().getId());
        return ResponseEntity.ok(ApiResponseBuilder.success(responseDTOs));
    }

    @GetMapping("/videos/main")
    @Operation(summary = "전체 영상 요청중 category로 필터링")
    public ResponseEntity<?> getCategoryFilterVideo(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "category", defaultValue = "0") int id) {
        VideoResponse.VideoPageResponseDTO responseDTOs = videoService.findAllVideo(page, id);
        return ResponseEntity.ok(ApiResponseBuilder.success(responseDTOs));
    }

    @GetMapping("/videos/{id}")
    @Operation(summary = "영상 개인페이지 요청")
    public ResponseEntity<?> getVideoId(@PathVariable int id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        VideoResponse.VideoResponseDTO responseDTO = videoService.findVideo(id, userDetails);
        return ResponseEntity.ok(ApiResponseBuilder.success(responseDTO));
    }

    @GetMapping("/videos/history")
    @Operation(summary = "영상 시청기록 요청")
    public ResponseEntity<?> getVideoHistory(@RequestParam(value = "page", defaultValue = "0") Integer page,  @AuthenticationPrincipal  CustomUserDetails userDetails) {
        List<VideoResponse.VideoAllResponseDTO> responseDTO = videoService.findHistoryVideo(page, userDetails.getUser().getId());
        return ResponseEntity.ok(ApiResponseBuilder.success(responseDTO));
    }

    @PostMapping("/videos")
    @Operation(summary = "영상 올리기")
    public ResponseEntity<?> postVideo(@RequestBody @Valid VideoRequest.CreateDTO createDTO, @AuthenticationPrincipal  CustomUserDetails userDetails) {
        videoService.createVideo(createDTO, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.successWithNoContent());
    }
}
