package com.example.demo.mentoring.contact;

import com.example.demo.config.auth.CustomUserDetails;
import com.example.demo.config.utils.ApiResponseBuilder;
import com.example.demo.user.Role;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ContactRestController {

    private final ContactService contactService;

    @GetMapping(value = "/contacts")
    @Operation(summary = "contact 화면 조회", description = "멘토, 멘티 화면에 따라 적절한 화면을 보여준다.")
    public ResponseEntity<?> findAllContacts(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if ( userDetails.getUser().getRole() == Role.MENTEE ) {
            List<ContactResponse.ContactDashBoardMenteeDTO> responseDTO = contactService.findAllByMentee(userDetails.getUser());
            return ResponseEntity.ok(ApiResponseBuilder.success(responseDTO));
        }
        List<ContactResponse.ContactDashboardMentorDTO> responseDTO = contactService.findAllByMentor(userDetails.getUser());
        return ResponseEntity.ok(ApiResponseBuilder.success(responseDTO));
    }

    
    @GetMapping(value = "/contacts/postCounts")
    @Operation(summary = "게시글의 갯수 조회", description = "멘토, 멘티 화면에 따라 contact, done 화면의 게시글 갯수를 보여준다.")
    public ResponseEntity<?> postCounts(@AuthenticationPrincipal CustomUserDetails userDetails) {
        // TO-DO : contact, done 옆 숫자를 띄우는 API 로직 만들기 ( 멘토, 멘티 나눠서 )

        // 멘토, 멘티 구분
        Role role = userDetails.getUser().getRole();

        if ( role == Role.MENTEE ) {
            ContactResponse.PostCountDTO responseDTO = contactService.postCountsMyMentee(userDetails.getUser().getId());
            return ResponseEntity.ok(ApiResponseBuilder.success(responseDTO));
        }

        ContactResponse.PostCountDTO responseDTO = contactService.postCountsByMentor(userDetails.getUser().getId());
        return ResponseEntity.ok(ApiResponseBuilder.success(responseDTO));
    }

    @PostMapping(value = "/contacts/accept")
    @Operation(summary = "멘토링 신청 수락", description = "멘토링 신청을 수락한다.")
    public ResponseEntity<?> acceptContact(@RequestBody @Valid ContactRequest.ContactAcceptDTO contactAcceptDTO, Error errors, @AuthenticationPrincipal CustomUserDetails userDetails) {
        // TO-DO : 멘토링 신청 수락 API 로직 만들기
        contactService.acceptContact(contactAcceptDTO, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.successWithNoContent());
    }

    @PatchMapping(value = "/contacts/refuse")
    @Operation(summary = "멘토링 신청 거절", description = "멘토링 신청을 거절한다.")
    public ResponseEntity<?> refuseContact(@RequestBody @Valid ContactRequest.ContactRefuseDTO contactRefuseDTO, Error errors, @AuthenticationPrincipal CustomUserDetails userDetails) {
        // TO-DO : 멘토링 신청 거절 API 로직 만들기
        contactService.refuseContact(contactRefuseDTO, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.successWithNoContent());
    }

    @PostMapping(value = "/contacts")
    @Operation(summary = "멘티의 멘토링 신청", description = "멘토가 작성한 글을 보고, 멘티는 멘토링 신청을 할 수 있다.")
    public ResponseEntity<?> createContact(@RequestBody @Valid ContactRequest.ContactCreateDTO contactCreateDTO, Error errors, @AuthenticationPrincipal CustomUserDetails userDetails) {
        // TO-DO : 멘토링 신청 API 로직 만들기
        contactService.createContact(contactCreateDTO, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.successWithNoContent());
    }

    @DeleteMapping(value = "/contacts")
    @Operation(summary = "멘티의 멘토링 신청 취소", description = "멘티는 신청한 멘토링을 취소할 수 있다.")
    public ResponseEntity<?> deleteContact(@RequestParam("connectionId") List<Integer> connectionId, Error errors, @AuthenticationPrincipal CustomUserDetails userDetails) {
        // TO-DO : 멘토링 신청 취소 API 로직 만들기
        contactService.deleteContact(connectionId, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.successWithNoContent());
    }

}
