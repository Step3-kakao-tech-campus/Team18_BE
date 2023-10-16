package com.example.demo.mentoring.contact;

import com.example.demo.config.auth.CustomUserDetails;
import com.example.demo.config.utils.ApiUtils;
import com.example.demo.user.Role;
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
    @Operation(summary = "contact 화면 조회", description = "멘토, 멘티 화면에 따라 적절한 화면을 보여준다.")
    public ResponseEntity<?> findAll(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if ( userDetails.getUser().getRole() == Role.MENTEE ) {
            List<ContactResponse.MenteeContactDTO> responseDTO = contactService.findAllByMentee(userDetails.getUser().getId());
            return ResponseEntity.ok(ApiUtils.success(responseDTO));
        }
        List<ContactResponse.MentorPostDTO> responseDTO = contactService.findAllByMentor(userDetails.getUser().getId());
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    
    @GetMapping(value = "/contacts/postCounts")
    @Operation(summary = "게시글의 갯수 조회", description = "멘토, 멘티 화면에 따라 contact, done 화면의 게시글 갯수를 보여준다.")
    public ResponseEntity<?> postCounts(@AuthenticationPrincipal CustomUserDetails userDetails) {
        // TO-DO : contact, done 옆 숫자를 띄우는 API 로직 만들기 ( 멘토, 멘티 나눠서 )

        // 멘토, 멘티 구분
        Role role = userDetails.getUser().getRole();

        if ( role == Role.MENTEE ) {
            return null;
        }

        ContactResponse.postCountDTO responseDTO = contactService.postCountsByMentor(userDetails.getUser().getId());
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

}
