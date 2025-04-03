package com.walnet.backend.domain.auth.controller;

import com.walnet.backend.domain.auth.dto.*;
import com.walnet.backend.domain.auth.service.AuthService;
import com.walnet.backend.domain.member.dto.SignUpDto;
import com.walnet.backend.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody VerifyAccountCodeRequsest request) {
        SignUpDto dto = SignUpDto.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .name(request.getName())
                .accountNumber(request.getAccountNumber())
                .bankName(request.getBankName())
                .build();
        memberService.signUp(dto);
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
    public ResponseEntity<String> sendVerificationCode(@RequestBody SendEmailCodeRequest sendEmailCodeRequest) {
        authService.sendVerificationCode(sendEmailCodeRequest.getEmail());
        log.info("{}로 인증코드 전송 완료", sendEmailCodeRequest.getEmail());
        return ResponseEntity.ok().body(sendEmailCodeRequest.getEmail() + "로 인증코드 전송 완료");
    }

    @PostMapping("/email/verify")
    public ResponseEntity<String> verifyCode(@RequestBody VerifyCodeRequest verifyCodeRequest) {
        authService.verifyEmail(verifyCodeRequest.getEmail(), verifyCodeRequest.getCode());
        log.info("{}로 인증 완료", verifyCodeRequest.getEmail());
        return ResponseEntity.ok().body("인증 성공");
    }

    @PostMapping("/account/send")
    public ResponseEntity<String> sendVerificationCodeToAccount(@RequestBody SendAccountCodeRequest sendAccountCodeRequest) {
        authService.sendVerificationCodeToAccount(sendAccountCodeRequest);
        return ResponseEntity.ok().body(sendAccountCodeRequest.getAccountNumber() + "로 인증코드 전송 완료");
    }

}
