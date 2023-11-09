package com.example.demo.video.controller;

import com.example.demo.config.security.CustomUserDetails;
import com.example.demo.config.utils.ApiResponseBuilder;
import com.example.demo.video.service.VideoService;
import com.example.demo.video.dto.VideoRequest;
import com.example.demo.video.dto.VideoResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

//@Api(tags = "Video API")
@RequiredArgsConstructor
@RestController
public class VideoRestController {

    private final VideoService videoService;

//    @PostMapping("/videos")
//    @Operation(summary = "영상 올리기")
//    public ResponseEntity<?> postVideo(@RequestBody @Valid VideoRequest.VideoDTO requestDTO, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails) {
//        videoService.createVideo(requestDTO, userDetails);
//        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.successWithNoContent());
//    }

    @GetMapping("/videos")
    @Operation(summary = "전체 영상 요청중 category로 필터링")
    public ResponseEntity<?> getCategoryFilterVideo(@RequestParam(value = "category", required = false) String category) {
        List<VideoResponse.VideoPageResponseDTO> responseDTOs = videoService.findAllVideo(category);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.success(responseDTOs));
    }

   @GetMapping("/videos/interest")
   @Operation(summary = "유저의 흥미있는 영상 요청")
    public ResponseEntity<?> getUserCategoryVideo(@AuthenticationPrincipal CustomUserDetails userDetails){
        List<VideoResponse.VideoAllResponseDTO> responseDTOs = videoService.findUserCategory(userDetails.getUser().getId());
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
    public ResponseEntity<?> getVideoHistory(@RequestParam(value = "page", defaultValue = "0") Integer page, @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<VideoResponse.VideoAllResponseDTO> responseDTO = videoService.findHistoryVideo(page, userDetails.getUser().getId());
        return ResponseEntity.ok(ApiResponseBuilder.success(responseDTO));
    }
}
