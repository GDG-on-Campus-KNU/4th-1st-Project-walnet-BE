package com.walnet.backend.domain.member.service;

import com.walnet.backend.domain.account.entity.Account;
import com.walnet.backend.domain.account.repository.AccountRepository;
import com.walnet.backend.domain.auth.repository.EmailVerificationRepository;
import com.walnet.backend.domain.member.dto.MemberInfoResponse;
import com.walnet.backend.domain.member.dto.SignUpDto;
import com.walnet.backend.domain.member.dto.WalletInfo;
import com.walnet.backend.domain.member.entity.Member;
import com.walnet.backend.domain.member.repository.MemberRepository;
import com.walnet.backend.domain.wallet.entity.CurrencyEnum;
import com.walnet.backend.domain.wallet.entity.Wallet;
import com.walnet.backend.domain.wallet.repository.WalletRepository;
import com.walnet.backend.global.email.GmailSender;
import com.walnet.backend.global.exception.BusinessException;
import com.walnet.backend.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final AccountRepository accountRepository;
    private final EmailVerificationRepository emailVerificationRepository;
    private final PasswordEncoder passwordEncoder;
    private final WalletRepository walletRepository;

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
        Wallet newWallet = Wallet.create(CurrencyEnum.KRW, 0L, newMember);

        memberRepository.save(newMember);
        accountRepository.save(newAccount);
        walletRepository.save(newWallet);
    }

    public MemberInfoResponse getMemberInfo(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
        List<Wallet> byMember = walletRepository.findByMember(member);

        List<WalletInfo> walletInfos = byMember.stream()
                .map(wallet -> WalletInfo.builder()
                        .id(wallet.getId())
                        .currency(wallet.getCurrency())
                        .balance(wallet.getBalance())
                        .build())
                .toList();

        MemberInfoResponse wallets = MemberInfoResponse.builder()
                .name(member.getName())
                .email(member.getEmail())
                .wallets(walletInfos)
                .build();

        return wallets;
    }
}
