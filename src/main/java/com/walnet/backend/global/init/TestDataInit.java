package com.walnet.backend.global.init;

import com.walnet.backend.domain.account.entity.Account;
import com.walnet.backend.domain.account.entity.BankEnum;
import com.walnet.backend.domain.account.repository.AccountRepository;
import com.walnet.backend.domain.auth.Entity.EmailVerification;
import com.walnet.backend.domain.auth.repository.EmailVerificationRepository;
import com.walnet.backend.domain.member.entity.Member;
import com.walnet.backend.domain.member.repository.MemberRepository;
import com.walnet.backend.domain.wallet.entity.CurrencyEnum;
import com.walnet.backend.domain.wallet.entity.Wallet;
import com.walnet.backend.domain.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
public class TestDataInit {
    private final MemberRepository memberRepository;
    private final EmailVerificationRepository emailVerificationRepository;
    private final PasswordEncoder passwordEncoder;
    private final WalletRepository walletRepository;
    private final AccountRepository accountRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void init() {
        log.info("test data init");
        String password = passwordEncoder.encode("aaaa");
        Member testMember = Member.create("이동재", password, "leedongjae625@gmail.com");
        memberRepository.save(testMember);

        EmailVerification testEmail = EmailVerification.create("leedongjae625@gmail.com", "000000");
        testEmail.verify("000000");
        emailVerificationRepository.save(testEmail);

        Wallet testKRW = Wallet.create(CurrencyEnum.KRW, 231890L, testMember);
        Wallet testUSD = Wallet.create(CurrencyEnum.USD, 231890L, testMember);
        Wallet testJPY = Wallet.create(CurrencyEnum.JPY, 1250L, testMember);
        walletRepository.save(testKRW);
        walletRepository.save(testUSD);
        walletRepository.save(testJPY);

        Account testAccount = Account.create(testMember, "101010101010", BankEnum.Deagu);
        accountRepository.save(testAccount);
    }
}
