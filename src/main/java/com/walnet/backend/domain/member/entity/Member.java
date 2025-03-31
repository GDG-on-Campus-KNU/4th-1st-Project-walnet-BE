package com.walnet.backend.domain.member.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String password;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @Email
    @Column(nullable = false, unique = true)
    private String email;


    //==연관관계 편의 메서드==//

    //==생성 메서드==//
    private Member(String name, String password, String email) {
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public static Member create(String name, String encodedPassword, String email) {
        return new Member(name, encodedPassword, email);
    }

    //==비즈니스 로직==//


}
