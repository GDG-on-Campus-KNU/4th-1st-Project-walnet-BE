package com.walnet.backend.domain.auth.Entity;

import com.walnet.backend.global.exception.BusinessException;
import com.walnet.backend.global.exception.ErrorCode;
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
    @Column(unique = true)
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
        this.code = newCode;
        this.createdAt = LocalDateTime.now();
        this.verified = false;
    }

    public void verify(String inputCode) {
        if (!this.code.equals(inputCode)) {
            throw new BusinessException(ErrorCode.INVALID_CODE);
        }
        if (this.createdAt.plus(VALID_DURATION).isBefore(LocalDateTime.now())) {
            throw new BusinessException(ErrorCode.EXPIRED_CODE);
        }
        this.verified = true;
    }
}
