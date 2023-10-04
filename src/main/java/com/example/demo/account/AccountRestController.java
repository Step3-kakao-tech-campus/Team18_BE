package com.example.demo.account;

import com.example.demo.config.utils.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class AccountRestController {
    private final AccountService accountService;

    @PostMapping(value = "/users/signup")
    @Operation(summary = "회원가입", description = "SignUpDTO 를 받아서 회원가입 진행")
    public ResponseEntity<?> signup(@RequestPart @Valid AccountRequest.SignUpDTO requestDTO, Errors errors) {
        accountService.signup(requestDTO);
        return ResponseEntity.ok().body(ApiUtils.success(true));
    }
}
