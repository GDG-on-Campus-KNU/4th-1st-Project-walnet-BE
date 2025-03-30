package com.walnet.backend.domain.auth.dto;

import jakarta.validation.constraints.Email;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class SendCodeRequest {
    @Email(message = "이메일 형식이 잘못되었습니다.")
    private String email;
}
