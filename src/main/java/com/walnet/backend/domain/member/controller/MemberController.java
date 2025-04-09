package com.walnet.backend.domain.member.controller;

import com.walnet.backend.domain.auth.jwt.UserDetail;
import com.walnet.backend.domain.member.dto.MemberInfoResponse;
import com.walnet.backend.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "회원", description = "회원 관련 API")
@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 정보 조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패 - JWT 누락 또는 만료",content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "MEMBER_NOT_FOUND",content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @Operation(
            summary = "회원 정보 조회",
            description = "JWT를 통해 인증된 회원의 정보를 조회합니다."
    )
    @GetMapping
    public ResponseEntity<MemberInfoResponse> memberInfo(@AuthenticationPrincipal UserDetail userDetail) {
        String email = userDetail.getUsername();
        log.info("홈화면 멤버 정보 조회 api, email = {}", email);
        MemberInfoResponse memberInfo = memberService.getMemberInfo(email);

        return ResponseEntity.ok().body(memberInfo);
    }

}
