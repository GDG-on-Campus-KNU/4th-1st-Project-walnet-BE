package com.walnet.backend.domain.auth.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class TokenRefreshRequest {
    private String refreshToken;
}
