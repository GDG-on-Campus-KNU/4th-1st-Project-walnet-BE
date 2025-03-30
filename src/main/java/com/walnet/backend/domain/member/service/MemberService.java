package com.walnet.backend.domain.member.service;

import com.walnet.backend.domain.auth.repository.EmailVerificationRepository;
import com.walnet.backend.domain.member.repository.MemberRepository;
import com.walnet.backend.global.email.GmailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final EmailVerificationRepository emailVerificationRepository;
    private final GmailSender gmailSender;
    private final PasswordEncoder passwordEncoder;



}
