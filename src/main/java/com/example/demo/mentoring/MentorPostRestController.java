package com.example.demo.mentoring;

import com.example.demo.config.auth.CustomUserDetails;
import com.example.demo.config.utils.ApiUtils;
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
        return ResponseEntity.status(HttpStatus.OK).body(ApiUtils.successWithNoContent());
    }

    @GetMapping("/mentorings/post")
    public ResponseEntity<?> getMentorPost(@RequestParam(value = "page", defaultValue = "0") Integer page, @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<MentorPostResponse.MentorPostAllDTO> responseDTOs = mentorPostService.findAllMentorPost(page);
        return ResponseEntity.ok(ApiUtils.success(responseDTOs));
    }

    @GetMapping("/mentorings/post/{id}")
    public ResponseEntity<?> getMentorPostId(@PathVariable int id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        MentorPostResponse.MentorPostDTO responseDTO = mentorPostService.findMentorPost(id);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    @PutMapping(value = "/mentorings/post/{id}")
    public ResponseEntity<?> updateMentorPost(@PathVariable int id, @RequestBody @Valid MentorPostRequest.CreateDTO requestDTO, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails) {
        mentorPostService.updateMentorPost(requestDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiUtils.successWithNoContent());
    }
}
