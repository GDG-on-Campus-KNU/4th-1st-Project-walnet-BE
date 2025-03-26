package com.walnet.backend.domain.member.entity;

import com.walnet.backend.domain.account.entity.Account;
import com.walnet.backend.domain.wallet.entity.Wallet;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @NotBlank
    @Column(name = "member_name")
    private String name;

    @NotBlank
    private String loginId;

    @NotBlank
    private String password;

    @OneToMany(mappedBy = "member")
    private List<Account> accounts;

    @OneToMany(mappedBy = "member")
    private List<Wallet> wallets;
}
