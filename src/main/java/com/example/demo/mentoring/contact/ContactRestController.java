package com.example.demo.mentoring.contact;

import com.example.demo.config.auth.CustomUserDetails;
import com.example.demo.config.utils.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ContactRestController {

    private final ContactService contactService;

    @GetMapping(value = "/contacts")
    @Operation(summary = "", description = "")
    public ResponseEntity<?> findAll(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<ContactResponse.MentorPostDTO> responseDTO = contactService.findAll(userDetails.getUser());
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

}
