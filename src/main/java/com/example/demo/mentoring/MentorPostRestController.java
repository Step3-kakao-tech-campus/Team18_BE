package com.example.demo.mentoring;

import com.example.demo.config.auth.CustomUserDetails;
import com.example.demo.config.utils.ApiResponseBuilder;
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

    @PostMapping(value = "/mentorings/post")
    public ResponseEntity<?> createMentorPost(@RequestBody @Valid MentorPostRequest.CreateDTO requestDTO, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails) {
        mentorPostService.createMentorPost(requestDTO, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.successWithNoContent());
    }

    @GetMapping("/mentorings/post")
    public ResponseEntity<?> getMentorPost(
            @RequestParam(value = "category", required = false) MentorPostCategoryEnum category,
            @RequestParam(value = "search", defaultValue = "") String keyword,
            @RequestParam(value = "page", defaultValue = "0") Integer page) {

        if(category == null)
            category = MentorPostCategoryEnum.NULL;

        List<MentorPostResponse.MentorPostAllDTO> responseDTOs = mentorPostService.findAllMentorPost(category, keyword, page);
        return ResponseEntity.ok(ApiUtils.success(responseDTOs));
    }

    @GetMapping("/mentorings/post/{id}")
    public ResponseEntity<?> getMentorPostId(@PathVariable int id) {
        MentorPostResponse.MentorPostDTO responseDTO = mentorPostService.findMentorPost(id);
        return ResponseEntity.ok(ApiResponseBuilder.success(responseDTO));
    }

    @PutMapping(value = "/mentorings/post/{id}")
    public ResponseEntity<?> updateMentorPost(@PathVariable int id, @RequestBody @Valid MentorPostRequest.CreateDTO requestDTO, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails) {
        mentorPostService.updateMentorPost(requestDTO, id, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(ApiUtils.successWithNoContent());
    }

    @DeleteMapping(value = "/mentorings/post/{id}")
    public ResponseEntity<?> deleteMentorPost(@PathVariable int id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        mentorPostService.deleteMentorPost(id, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(ApiUtils.successWithNoContent());
    }

    @PatchMapping(value = "/mentorings/post/{id}/done")
    public ResponseEntity<?> changeMentorPostStatus(@PathVariable int id,@RequestBody @Valid MentorPostRequest.StateDTO requestDTO, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails) {
        mentorPostService.changeMentorPostStatus(requestDTO, id, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(ApiUtils.successWithNoContent());
    }

}
