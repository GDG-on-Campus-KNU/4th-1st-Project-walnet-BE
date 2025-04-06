package com.walnet.backend.domain.account.entity;

import com.walnet.backend.domain.member.entity.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @NotBlank
    @Column(nullable = false)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private BankEnum bankName;


    private Account (Member member, String accountNumber, BankEnum bankName) {
        this.member = member;
        this.accountNumber = accountNumber;
        this.bankName = bankName;
    }

    public static Account create (Member member, String accountNumber, BankEnum bankName) {
        return new Account(member, accountNumber, bankName);
    }
}
