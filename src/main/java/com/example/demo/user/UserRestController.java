package com.example.demo.user;

import com.example.demo.config.jwt.JWTTokenProvider;
import com.example.demo.config.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserRestController {

    private final UserService userService;

    @PostMapping(value = "/users/emailcheck")
    public ResponseEntity<?> emailCheck(@RequestBody @Valid UserRequest.EmailCheckDTO requestDTO, Errors errors) {
        userService.emailCheck(requestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(ApiUtils.successWithNoContent());
    }

    @PostMapping(value = "/users/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid UserRequest.SignUpDTO requestDTO, Errors errors) {
        userService.signup(requestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(ApiUtils.successWithNoContent());
    }

    @PostMapping(value = "/users/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserRequest.LoginDTO requestDTO, Errors errors) {
        UserResponse.LoginDTO responseDTO = userService.login(requestDTO);
        return ResponseEntity.ok().header(JWTTokenProvider.HEADER, responseDTO.getJWTToken()).body(ApiUtils.success(responseDTO.getUserDetailDTO()));
    }
}
