package com.example.demo.mentoring;

import com.example.demo.config.auth.CustomUserDetails;
import com.example.demo.config.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MentorPostRestController {
    private final MentorPostService mentorPostService;

    @PostMapping(value = "/mentorings/post")
    public ResponseEntity<?> createMentorPost(@RequestPart MentorPostRequest.CreateDTO requestDTO, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails) {
        mentorPostService.createMentorPost(requestDTO, userDetails.getUser());
        return ResponseEntity.ok().body(ApiUtils.success(true));
    }

    @GetMapping("/mentorings/post")
    public ResponseEntity<?> getMentorPost(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        List<MentorPostResponse.MentorPostAllDTO> responseDTOs = mentorPostService.findAllMentorPost(page);
        return ResponseEntity.ok(ApiUtils.success(responseDTOs));
    }
}
