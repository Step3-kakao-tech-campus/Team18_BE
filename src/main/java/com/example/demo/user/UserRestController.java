package com.example.demo.user;

import com.example.demo.config.jwt.JWTTokenProvider;
import com.example.demo.config.utils.ApiUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "User API")
@RequiredArgsConstructor
@RestController
public class UserRestController {

    private final UserService userService;

    @Operation(summary = "이메일 중복 확인", description = "동일한 이메일이 존재하는지 확인한다.")
    @PostMapping(value = "/users/emailcheck")
    public ResponseEntity<?> emailCheck(@RequestBody @Valid UserRequest.EmailCheckDTO requestDTO, Errors errors) {
        userService.emailCheck(requestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(ApiUtils.successWithNoContent());
    }

    @Operation(summary = "회원가입", description = "회원가입")
    @PostMapping(value = "/users/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid UserRequest.SignUpDTO requestDTO, Errors errors) {
        userService.signup(requestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(ApiUtils.successWithNoContent());
    }

    @Operation(summary = "로그인", description = "로그인")
    @PostMapping(value = "/users/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserRequest.LoginDTO requestDTO, Errors errors) {
        UserResponse.LoginDTO responseDTO = userService.login(requestDTO);
        return ResponseEntity.status(HttpStatus.OK).header(JWTTokenProvider.HEADER, responseDTO.getJWTToken()).body(ApiUtils.success(responseDTO.getUserDetailDTO()));
    }

    @Operation(summary = "마이 페이지 프로필 확인", description = "마이 페이지에서 프로필 확인")
    @GetMapping(value = "/profiles/{id}")
    public ResponseEntity<?> profile(@PathVariable int id) {
        UserResponse.ProfileDTO responseDTO = userService.findProfile(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiUtils.success(responseDTO));
    }
    
    @Operation(summary = "마이 페이지 프로필 수정", description = "마이 페이지에서 프로필 수정")
    @PutMapping(value = "/profiles/{id}")
    public ResponseEntity<?> profileUpdate(@PathVariable int id, @RequestBody @Valid UserRequest.ProfileUpdateDTO requestDTO, Errors errors) {
        UserResponse.ProfileDTO responseDTO = userService.updateProfile(id, requestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(ApiUtils.success(responseDTO));
    }
}
