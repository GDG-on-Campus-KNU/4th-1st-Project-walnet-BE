package com.walnet.backend.domain.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class EmailVerification {
    public static final Duration VALID_DURATION = Duration.ofMinutes(3);
    
    @Id
    @Email
    private String email;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private boolean verified = false;

    //==생성 메서드==//
    private EmailVerification(String email, String code) {
        this.email = email;
        this.code = code;
        this.createdAt = LocalDateTime.now();
        this.verified = false;
    }

    public static EmailVerification create(String email, String code) {
        return new EmailVerification(email, code);
    }

    //==비즈니스 로직==//
    public void regenerateCode(String newCode) {
        if (this.verified) {
            throw new IllegalStateException("이미 인증된 이메일은 재발급할 수 없습니다.");
        } else {
            this.code = newCode;
            this.createdAt = LocalDateTime.now();
        }
    }

    public void verify(String inputCode) {
        if (this.verified) {
            throw new IllegalStateException("이미 인증된 이메일입니다.");
        } else if (!this.code.equals(inputCode)) {
            throw new IllegalArgumentException("인증 코드가 다릅니다.");
        } else if (this.createdAt.plus(VALID_DURATION).isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("인증 코드 만료");
        }
        this.verified = true;
    }


}
