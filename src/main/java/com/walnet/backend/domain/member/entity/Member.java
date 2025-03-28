package com.walnet.backend.domain.member.entity;

import com.walnet.backend.domain.account.entity.Account;
import com.walnet.backend.domain.wallet.entity.Wallet;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Getter
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

    @Email
    private String email;

    @NotBlank
    private String password;

    //==연관관계 편의 메서드==//

    //==생성 메서드==//

    //==비즈니스 로직==//

}
