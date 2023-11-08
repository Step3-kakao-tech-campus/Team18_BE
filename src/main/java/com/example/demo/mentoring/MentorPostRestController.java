package com.example.demo.mentoring;

import com.example.demo.config.auth.CustomUserDetails;
import com.example.demo.config.utils.ApiResponseBuilder;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class MentorPostRestController {
    private final MentorPostService mentorPostService;

    @PostMapping(value = "/mentorings")
    @Operation(summary = "mentorpost 생성")
    public ResponseEntity<?> createMentorPost(@RequestBody @Valid MentorPostRequest.CreateMentorPostDTO requestDTO, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails) {
        mentorPostService.createMentorPost(requestDTO, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.successWithNoContent());
    }

    @GetMapping("/mentorings")
    @Operation(summary = "mentorpost 가져오기", description = "category, search로 필터링, pagination 적용")
    public ResponseEntity<?> getMentorPost(
            @RequestParam(value = "category", defaultValue = "NULL") String category,
            @RequestParam(value = "search", defaultValue = "") String keyword,
            @RequestParam(value = "page", defaultValue = "0") Integer page) {

        List<MentorPostResponse.MentorPostAllDTO> responseDTOs = mentorPostService.findAllMentorPost(category, keyword, page);
        return ResponseEntity.ok(ApiResponseBuilder.success(responseDTOs));
    }

    @GetMapping("/mentorings/{id}")
    @Operation(summary = "mentorpost 개별 페이지 불러오기")
    public ResponseEntity<?> getMentorPostId(@PathVariable int id) {
        MentorPostResponse.MentorPostDTO responseDTO = mentorPostService.findMentorPost(id);
        return ResponseEntity.ok(ApiResponseBuilder.success(responseDTO));
    }

    @PutMapping(value = "/mentorings/{id}")
    @Operation(summary = "mentorpost 수정 요청")
    public ResponseEntity<?> updateMentorPost(@PathVariable int id, @RequestBody @Valid MentorPostRequest.CreateMentorPostDTO requestDTO, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails) {
        mentorPostService.updateMentorPost(requestDTO, id, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.successWithNoContent());
    }

    @DeleteMapping(value = "/mentorings/{id}")
    @Operation(summary = "mentorpost 삭제 요청")
    public ResponseEntity<?> deleteMentorPost(@PathVariable int id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        mentorPostService.deleteMentorPost(id, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.successWithNoContent());
    }

    @PatchMapping(value = "/mentorings/{id}/done")
    @Operation(summary = "mentorpost 만료 요청")
    public ResponseEntity<?> changeMentorPostStatus(@PathVariable int id,@RequestBody @Valid MentorPostRequest.StateDTO requestDTO, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails) {
        mentorPostService.changeMentorPostStatus(requestDTO, id, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.successWithNoContent());
    }

}
