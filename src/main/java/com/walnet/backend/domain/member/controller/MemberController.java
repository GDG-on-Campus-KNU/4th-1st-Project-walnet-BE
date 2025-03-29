package com.walnet.backend.domain.member.controller;

import com.walnet.backend.domain.member.dto.SendCodeRequest;
import com.walnet.backend.domain.member.dto.VerifyCodeRequest;
import com.walnet.backend.domain.member.service.MemberService;
import com.walnet.backend.global.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/email/send")
    public ResponseEntity<String> sendVerificationCode(@RequestBody SendCodeRequest sendCodeRequest) {
        memberService.sendVerificationCode(sendCodeRequest.getEmail());
        log.info("{}로 인증코드 전송 완료",sendCodeRequest.getEmail());
        return ResponseEntity.ok().body(sendCodeRequest.getEmail()+"로 인증코드 전송 완료");
    }

    @PostMapping("/email/verify")
    public ResponseEntity<Object> verifyCode(@RequestBody VerifyCodeRequest verifyCodeRequest) {
        memberService.verifyEmail(verifyCodeRequest.getEmail(), verifyCodeRequest.getCode());
        log.info("{}로 인증 완료",verifyCodeRequest.getEmail());
        return ResponseEntity.ok().body("인증 성공");
    }
}
