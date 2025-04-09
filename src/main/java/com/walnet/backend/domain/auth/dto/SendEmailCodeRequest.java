package com.walnet.backend.domain.auth.dto;

import jakarta.validation.constraints.Email;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class SendEmailCodeRequest {
    @Email(message = "이메일 형식이 잘못되었습니다.")
    private String email;
}
