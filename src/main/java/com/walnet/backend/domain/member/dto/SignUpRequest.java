package com.walnet.backend.domain.member.dto;

import jakarta.validation.constraints.Email;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class SingUpRequest {
    private String name;
    private String
    @Email
    private String email;

}
