package com.walnet.backend.domain.auth.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class LoginRequest {
    private String email;
    private String password;
}
