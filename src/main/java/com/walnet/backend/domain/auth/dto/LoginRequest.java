package com.walnet.backend.domain.auth.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class LoginRequest {
    private String email;
    private String password;
}
