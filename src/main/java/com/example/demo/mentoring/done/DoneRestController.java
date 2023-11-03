package com.example.demo.mentoring.done;

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
public class DoneRestController {

    private final DoneService doneService;

    @GetMapping(value = "/contacts/done")
    @Operation(summary = "contact 화면 조회", description = "멘토, 멘티 화면에 따라 적절한 화면을 보여준다.")
    public ResponseEntity<?> findAllContacts(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if ( userDetails.getUser().getRole().equals(Role.MENTEE) ) {
            List<DoneResponse.DoneDashBoardDTO> responseDTO = doneService.findByMentee(userDetails.getUser());
            return ResponseEntity.ok(ApiUtils.success(responseDTO));
        }
        List<DoneResponse.DoneDashBoardDTO> responseDTO = doneService.findByMentor(userDetails.getUser());
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
}
