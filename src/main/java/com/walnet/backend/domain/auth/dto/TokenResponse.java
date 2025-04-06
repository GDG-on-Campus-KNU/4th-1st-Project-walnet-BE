package com.walnet.backend.domain.auth.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class TokenResponse {
    String accessToken;
    String refreshToken;
}
