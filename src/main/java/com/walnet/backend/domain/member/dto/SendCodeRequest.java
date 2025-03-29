package com.walnet.backend.domain.member.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class SendCodeRequest {
    private String email;
}
