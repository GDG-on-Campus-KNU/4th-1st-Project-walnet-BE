package com.walnet.backend.domain.auth.controller;

import com.walnet.backend.domain.auth.dto.*;
import com.walnet.backend.domain.auth.service.AuthService;
import com.walnet.backend.domain.member.dto.SignUpDto;
import com.walnet.backend.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;


import java.io.IOException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "인증 API", description = "회원가입, 로그인, 이메일/계좌 인증 관련 API")
public class AuthController {

    private final AuthService authService;
    private final MemberService memberService;

    @Operation(
            summary = "계좌인증 && 회원가입",
            description = "계좌 인증 할 때 호출하면 됩니다. 회원가입을 처리합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "회원가입 성공"),
                    @ApiResponse(responseCode = "403", description = "UNVERIFIED_EMAIL - 이메일 인증이 안됨", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
                    @ApiResponse(responseCode = "404", description = "EMAIL_NOT_FOUND - 존재하지 않는 이메일입니다.", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
                    @ApiResponse(responseCode = "409", description = "DUPLICATE_EMAIL - 이미 가입된 이메일입니다.", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
            }
    )
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

    @Operation(
            summary = "로그인",
            description = "이메일과 비밀번호로 로그인합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(schema = @Schema(implementation = TokenResponse.class))),
                    @ApiResponse(responseCode = "401", description = "PASSWORD_NOT_MATCH - 비밀번호 틀림", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
                    @ApiResponse(responseCode = "404", description = "EMAIL_NOT_FOUND - 존재하지 않는 이메일입니다.", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
            }
    )
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest){
        log.info("로그인 요청, 이메일 = {}, 비밀번호 = {}", loginRequest.getEmail(), loginRequest.getPassword());
        TokenResponse tokenResponse = authService.login(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok().body(tokenResponse);
    }


    @Operation(
            summary = "Access Token 재발급",
            description = "Refresh Token을 통해 Access Token을 재발급받습니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "토큰 재발급 성공", content = @Content(schema = @Schema(implementation = TokenResponse.class)))
            }
    )
    @PostMapping("/refresh-token")
    public ResponseEntity<TokenResponse> refreshAccessToken(@RequestBody TokenRefreshRequest request) {
        TokenResponse tokenResponse = authService.refreshAccessToken(request.getRefreshToken());
        return ResponseEntity.ok().body(tokenResponse);
    }

    @Operation(
            summary = "이메일 인증 코드 전송",
            description = "회원가입을 위한 인증 코드를 이메일로 전송합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "이메일 전송 성공"),
                    @ApiResponse(responseCode = "400", description = "INVALID_EMAIL - 이메일 형식 오류", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
                    @ApiResponse(responseCode = "409", description = "EMAIL_ALREADY_VERIFIED - 이미 인증된 이메일", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
            }
    )
    @PostMapping("/email/send")
    public ResponseEntity<String> sendVerificationCode(@RequestBody SendEmailCodeRequest sendEmailCodeRequest) {
        authService.sendVerificationCode(sendEmailCodeRequest.getEmail());
        log.info("{}로 인증코드 전송 완료", sendEmailCodeRequest.getEmail());
        return ResponseEntity.ok().body(sendEmailCodeRequest.getEmail() + "로 인증코드 전송 완료");
    }

    @Operation(
            summary = "이메일 인증 코드 검증",
            description = "이메일로 받은 인증 코드를 검증합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "이메일 인증 성공"),
                    @ApiResponse(responseCode = "400", description = "INVALID_CODE - 인증코드 틀림", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
                    @ApiResponse(responseCode = "404", description = "EMAIL_NOT_FOUND - 존재하지 않는 이메일", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
            }
    )
    @PostMapping("/email/verify")
    public ResponseEntity<String> verifyCode(@RequestBody VerifyCodeRequest verifyCodeRequest) {
        authService.verifyEmail(verifyCodeRequest.getEmail(), verifyCodeRequest.getCode());
        log.info("{}로 인증 완료", verifyCodeRequest.getEmail());
        return ResponseEntity.ok().body("인증 성공");
    }

    @Operation(
            summary = "계좌 인증 코드 전송",
            description = "입력한 계좌로 인증 코드를 전송합니다. 사실은 이메일로 전송합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "계좌 인증 코드 전송 성공")
            }
    )
    @PostMapping("/account/send")
    public ResponseEntity<String> sendVerificationCodeToAccount(@RequestBody SendAccountCodeRequest sendAccountCodeRequest) {
        authService.sendVerificationCodeToAccount(sendAccountCodeRequest);
        return ResponseEntity.ok().body(sendAccountCodeRequest.getAccountNumber() + "로 인증코드 전송 완료");
    }
}