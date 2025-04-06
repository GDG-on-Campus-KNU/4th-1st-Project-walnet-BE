package com.walnet.backend.domain.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class VerifyCodeRequest {
    @Email(message = "이메일 형식이 잘못되었습니다.")
    private String email;
    @Size(min = 6, max = 6, message = "인증 코드는 6자리여야 합니다.")
    private String code;
}
