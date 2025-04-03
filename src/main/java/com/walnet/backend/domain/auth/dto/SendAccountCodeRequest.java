package com.walnet.backend.domain.auth.dto;

import com.walnet.backend.domain.account.entity.BankEnum;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class SendAccountCodeRequest {
    @Email(message = "이메일 형식이 잘못되었습니다.")
    private String email;
    private String accountNumber;
    private BankEnum bankName;
}
