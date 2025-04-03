package com.walnet.backend.domain.member.dto;

import com.walnet.backend.domain.account.entity.BankEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class SignUpDto {
    @Email
    private String email;
    @NotBlank
    private String password;
    private String name;
    private String accountNumber;
    private BankEnum bankName;
}
