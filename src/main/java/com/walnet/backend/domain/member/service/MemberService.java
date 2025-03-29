package com.walnet.backend.domain.member.service;

import com.walnet.backend.domain.member.entity.EmailVerification;
import com.walnet.backend.domain.member.entity.Member;
import com.walnet.backend.domain.member.repository.EmailVerificationRepository;
import com.walnet.backend.domain.member.repository.MemberRepository;
import com.walnet.backend.global.email.GmailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final EmailVerificationRepository emailVerificationRepository;
    private final GmailSender gmailSender;

    public void signUp(Member member) {

    }

    public void sendVerificationCode(String email) {
        Optional<EmailVerification> byEmail = emailVerificationRepository.findByEmail(email);
        EmailVerification ev = null;
        String code = generate6DigitCode();
        if (byEmail.isPresent()) {
            ev = byEmail.get();
            ev.regenerateCode(code);
        } else {
            ev = EmailVerification.create(email, code);
        }
        emailVerificationRepository.save(ev);
        gmailSender.send(email, "walnet 이메일 인증코드", "당신의 인증코드는 \"" + code + "\"입니다.");
    }

    public void verifyEmail(String email, String code) {
        Optional<EmailVerification> byEmail = emailVerificationRepository.findByEmail(email);
        if (byEmail.isPresent()) {
            byEmail.get().verify(code);
        }
    }

    private String generate6DigitCode() {
        int codeNum = ThreadLocalRandom.current().nextInt(0, 1000000);
        return String.format("%06d", codeNum);
    }
}
