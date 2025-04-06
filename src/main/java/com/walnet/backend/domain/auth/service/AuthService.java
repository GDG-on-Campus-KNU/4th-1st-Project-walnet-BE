package com.walnet.backend.domain.auth.service;

import com.walnet.backend.domain.account.entity.BankEnum;
import com.walnet.backend.domain.auth.Entity.EmailVerification;
import com.walnet.backend.domain.auth.dto.SendAccountCodeRequest;
import com.walnet.backend.domain.auth.dto.TokenResponse;
import com.walnet.backend.domain.auth.jwt.JwtProvider;
import com.walnet.backend.domain.auth.repository.EmailVerificationRepository;
import com.walnet.backend.domain.member.entity.Member;
import com.walnet.backend.domain.member.repository.MemberRepository;
import com.walnet.backend.global.email.GmailSender;
import com.walnet.backend.global.exception.BusinessException;
import com.walnet.backend.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final EmailVerificationRepository emailVerificationRepository;
    private final GmailSender gmailSender;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public TokenResponse login(String email, String password) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        if(!passwordEncoder.matches(password, member.getPassword())) {
            throw new BusinessException(ErrorCode.PASSWORD_NOT_MATCH);
        }

        String accessToken = jwtProvider.generateAccessToken(email);
        String refreshToken = jwtProvider.generateRefreshToken(email);

        return new TokenResponse(accessToken, refreshToken);
    }

    public void sendVerificationCode(String email) {
        //계정이 존재하는 이메일이다.
        if(memberRepository.findByEmail(email).isPresent()) {
            throw new BusinessException(ErrorCode.ALREADY_EXISTS_MEMBER);
        }

        Optional<EmailVerification> emailEm = emailVerificationRepository.findByEmail(email);
        EmailVerification ev = null;
        String code = generate6DigitCode();
        if(emailEm.isPresent()){
            ev = emailEm.get();
            ev.regenerateCode(code);
        } else {
            ev = EmailVerification.create(email, code);
        }
        emailVerificationRepository.save(ev);
        gmailSender.send(email, "walnet 이메일 인증코드", "당신의 인증코드는 \"" + code + "\"입니다.");
    }

    public void verifyEmail(String email, String code) {
        if(memberRepository.findByEmail(email).isPresent()) {
            throw new BusinessException(ErrorCode.ALREADY_EXISTS_MEMBER);
        }

        Optional<EmailVerification> byEmail = emailVerificationRepository.findByEmail(email);
        if (byEmail.isPresent()) {
            byEmail.get().verify(code);
        } else {
            throw new BusinessException(ErrorCode.EMAIL_NOT_FOUND);
        }
    }

    public TokenResponse refreshAccessToken(String token) {
        String refreshToken = token.replace("Bearer ", "");
        String email = jwtProvider.extractEmailFromRefreshToken(refreshToken);
        TokenResponse tokenResponse = jwtProvider.refreshAccessToken(refreshToken);
        return tokenResponse;
    }

    public void sendVerificationCodeToAccount(SendAccountCodeRequest request) {
        String accountNumber = request.getAccountNumber();
        BankEnum bankName = request.getBankName();
        String email = request.getEmail();
        String code = generate3DigitCode();



        gmailSender.send(email,"walnet 계좌 인증코드", bankName+" "+accountNumber+"\n입금자명: WAL"+code);
    }

    private String generate6DigitCode() {
        int codeNum = ThreadLocalRandom.current().nextInt(0, 1000000);
        return String.format("%06d", codeNum);
    }

    private String generate3DigitCode() {
        int codeNum = ThreadLocalRandom.current().nextInt(0, 1000);
        return String.format("%03d", codeNum);
    }


}
