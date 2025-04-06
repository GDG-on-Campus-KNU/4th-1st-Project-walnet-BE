package com.walnet.backend.domain.member.service;

import com.walnet.backend.domain.account.entity.Account;
import com.walnet.backend.domain.account.repository.AccountRepository;
import com.walnet.backend.domain.auth.repository.EmailVerificationRepository;
import com.walnet.backend.domain.member.dto.SignUpDto;
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


@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final AccountRepository accountRepository;
    private final EmailVerificationRepository emailVerificationRepository;
    private final PasswordEncoder passwordEncoder;

    //TODO: 회원가입 로직 확인해봐야할듯
    public void signUp(SignUpDto dto) {
        if (memberRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessException(ErrorCode.ALREADY_EXISTS_EMAIL);
        }
        if (!emailVerificationRepository.isEmailVerified(dto.getEmail())) {
            throw new BusinessException(ErrorCode.UNVERIFIED_EMAIL);
        }
        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        Member newMember = Member.create(dto.getName(), encodedPassword, dto.getEmail());
        Account newAccount = Account.create(newMember, dto.getAccountNumber(), dto.getBankName());
        memberRepository.save(newMember);
        accountRepository.save(newAccount);
    }
}
