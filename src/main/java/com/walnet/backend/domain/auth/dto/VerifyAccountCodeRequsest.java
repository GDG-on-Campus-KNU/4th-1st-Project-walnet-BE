package com.walnet.backend.domain.auth.dto;

import com.walnet.backend.domain.account.entity.BankEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class VerifyAccountCodeRequsest {
    @Email(message = "이메일 형식이 잘못되었습니다.")
    private String email;
    private String password;
    private String name;
    private String accountNumber;
    private BankEnum bankName;
    @Size(min = 3, max = 3, message = "인증 코드는 6자리여야 합니다.")
    private String code;
}
