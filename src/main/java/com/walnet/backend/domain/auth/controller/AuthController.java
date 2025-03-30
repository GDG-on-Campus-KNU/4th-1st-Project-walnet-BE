package com.walnet.backend.domain.auth.controller;

import com.walnet.backend.domain.auth.service.AuthService;
import com.walnet.backend.domain.auth.dto.LoginRequest;
import com.walnet.backend.domain.auth.dto.SendCodeRequest;
import com.walnet.backend.domain.auth.dto.SignUpRequest;
import com.walnet.backend.domain.auth.dto.VerifyCodeRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        authService.login(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok().body("");
    }

    @PostMapping("/email/send")
    public ResponseEntity<String> sendVerificationCode(@RequestBody SendCodeRequest sendCodeRequest) {
        authService.sendVerificationCode(sendCodeRequest.getEmail());
        log.info("{}로 인증코드 전송 완료",sendCodeRequest.getEmail());
        return ResponseEntity.ok().body(sendCodeRequest.getEmail()+"로 인증코드 전송 완료");
    }

    @PostMapping("/email/verify")
    public ResponseEntity<Object> verifyCode(@RequestBody VerifyCodeRequest verifyCodeRequest) {
        authService.verifyEmail(verifyCodeRequest.getEmail(), verifyCodeRequest.getCode());
        log.info("{}로 인증 완료",verifyCodeRequest.getEmail());
        return ResponseEntity.ok().body("인증 성공");
    }
}
