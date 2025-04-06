package com.walnet.backend.domain.member.controller;

import com.walnet.backend.domain.auth.jwt.UserDetail;
import com.walnet.backend.domain.member.dto.MemberInfoResponse;
import com.walnet.backend.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<MemberInfoResponse> memberInfo(@AuthenticationPrincipal UserDetail userDetail) {
        String email = userDetail.getUsername();
        log.info("홈화면 멤버 정보 조회 api, email = {}", email);
        MemberInfoResponse memberInfo = memberService.getMemberInfo(email);

        return ResponseEntity.ok().body(memberInfo);
    }

}
