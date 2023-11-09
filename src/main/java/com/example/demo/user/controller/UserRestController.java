package com.example.demo.user.controller;

import com.example.demo.config.security.CustomUserDetails;
import com.example.demo.config.security.JWTTokenProvider;
import com.example.demo.config.utils.ApiResponseBuilder;
import com.example.demo.user.dto.UserRequest;
import com.example.demo.user.dto.UserResponse;
import com.example.demo.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Tag(name = "User API", description = "User API")
@RequiredArgsConstructor
@RestController
public class UserRestController {

    private final UserService userService;

    @Operation(summary = "이메일 중복 확인", description = "동일한 이메일이 존재하는지 확인한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ApiResponseBuilder.ApiResponse.class))),
            @ApiResponse(responseCode = "400", description = "동일한 이메일이 존재합니다.", content = @Content(schema = @Schema(implementation = ApiResponseBuilder.ApiResponse.class)))
    })
    @PostMapping(value = "/users/emailcheck")
    public ResponseEntity<?> emailCheck(@RequestBody @Valid UserRequest.EmailCheckDTO requestDTO, Errors errors) {
        userService.emailCheck(requestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.successWithNoContent());
    }

    @Operation(summary = "회원가입", description = "회원가입")
    @PostMapping(value = "/users/signup", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> signup(@RequestPart @Valid UserRequest.SignUpDTO requestDTO, Errors errors, @RequestPart MultipartFile file) throws IOException {
        userService.signup(requestDTO, file);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.successWithNoContent());
    }

    @Operation(summary = "로그인", description = "로그인")
    @PostMapping(value = "/users/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserRequest.LoginDTO requestDTO, Errors errors, HttpServletResponse httpServletResponse) throws UnsupportedEncodingException {
        UserResponse.LoginDTO responseDTO = userService.login(requestDTO);

        String accessToken = JWTTokenProvider.Token_Prefix + responseDTO.getJwtToken().getAccessToken();
        String refreshToken = JWTTokenProvider.Token_Prefix + responseDTO.getJwtToken().getRefreshToken();
        refreshToken = URLEncoder.encode(refreshToken, "utf-8");

        httpServletResponse.addHeader(JWTTokenProvider.Header, accessToken);
        Cookie cookie = new Cookie("RefreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 7);
        httpServletResponse.addCookie(cookie);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.success(responseDTO.getUserDetailDTO()));
    }

    @Operation(summary = "Access Token 재발급", description = "Refresh Token을 이용하여 Access Token 재발급")
    @GetMapping(value = "/users/refresh")
    public ResponseEntity<?> refreshToken(@CookieValue(value = "RefreshToken") String refreshToken) throws UnsupportedEncodingException {
        String accessToken = userService.reissueAccessToken(refreshToken);
        return ResponseEntity.status(HttpStatus.OK).header(JWTTokenProvider.Header, accessToken).body(ApiResponseBuilder.successWithNoContent());
    }

    @Operation(summary = "간단한 프로필 정보", description = "간단한 프로필 정보")
    @GetMapping(value = "/profiles/simple")
    public ResponseEntity<?> simpleProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        UserResponse.SimpleProfileDTO responseDTO = userService.findSimpleProfile(userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.success(responseDTO));
    }

    @Operation(summary = "마이 페이지 프로필 확인", description = "마이 페이지에서 프로필 확인")
    @GetMapping(value =  {"/profiles", "/profiles/{id}"})
    public ResponseEntity<?> profile(@PathVariable(required = false) Integer id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        UserResponse.ProfileDTO responseDTO = userService.findProfile(id, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.success(responseDTO));
    }

    @Operation(summary = "마이 페이지 프로필 수정 전 본인 확인", description = "마이 페이지 프로필 수정 전 본인 확인")
    @PostMapping(value = "/users/passwordcheck")
    public ResponseEntity<?> passwordCheck(@RequestBody @Valid UserRequest.PasswordCheckDTO requestDTO, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails) {
        userService.passwordCheck(requestDTO, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.successWithNoContent());
    }

    @Operation(summary = "마이 페이지 프로필 수정", description = "마이 페이지에서 프로필 수정")
    @PutMapping(value = "/profiles", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> putProfile(@RequestPart @Valid UserRequest.ProfileUpdateDTO requestDTO, Errors errors, @RequestPart MultipartFile file, @AuthenticationPrincipal CustomUserDetails userDetails) throws IOException {
        UserResponse.ProfileDTO responseDTO = userService.updateProfile(userDetails, requestDTO, file);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.success(responseDTO));
    }
}
