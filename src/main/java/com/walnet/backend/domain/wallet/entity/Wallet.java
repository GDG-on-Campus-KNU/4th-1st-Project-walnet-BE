package com.walnet.backend.domain.wallet.entity;

import com.walnet.backend.domain.transaction.entity.Transaction;
import com.walnet.backend.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wallet_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "wallet_currency", nullable = false)
    private CurrencyEnum currency;

    @Column(nullable = false)
    private Long balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Wallet(CurrencyEnum currency, Long balance, Member member) {
        this.currency = currency;
        this.balance = balance;
        this.member = member;
    }

    public static Wallet create(CurrencyEnum currency, Long balance, Member member) {
        return new Wallet(currency, balance, member);
    }

}
