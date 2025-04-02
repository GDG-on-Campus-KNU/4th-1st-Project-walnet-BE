package com.walnet.backend.domain.auth.controller;

import com.walnet.backend.domain.auth.dto.*;
import com.walnet.backend.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequest signUpRequest) {
        authService.signUp(signUpRequest.getName(),signUpRequest.getPassword(),signUpRequest.getEmail());
        return ResponseEntity.ok().body("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
        TokenResponse tokenResponse = authService.login(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok().body(tokenResponse);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenResponse> refreshAccessToken(@RequestBody TokenRefreshRequest request) {
        TokenResponse tokenResponse = authService.refreshAccessToken(request.getRefreshToken());
        return ResponseEntity.ok().body(tokenResponse);
    }

    @PostMapping("/email/send")
    public ResponseEntity<String> sendVerificationCode(@RequestBody SendCodeRequest sendCodeRequest) {
        authService.sendVerificationCode(sendCodeRequest.getEmail());
        log.info("{}로 인증코드 전송 완료", sendCodeRequest.getEmail());
        return ResponseEntity.ok().body(sendCodeRequest.getEmail() + "로 인증코드 전송 완료");
    }

    @PostMapping("/email/verify")
    public ResponseEntity<String> verifyCode(@RequestBody VerifyCodeRequest verifyCodeRequest) {
        authService.verifyEmail(verifyCodeRequest.getEmail(), verifyCodeRequest.getCode());
        log.info("{}로 인증 완료",verifyCodeRequest.getEmail());
        return ResponseEntity.ok().body("인증 성공");
    }
}
