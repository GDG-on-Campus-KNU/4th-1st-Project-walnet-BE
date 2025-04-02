package com.walnet.backend.domain.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class SignUpRequest {
    @NotBlank
    private String password;

    @Email
    private String email;

    private String name;

}
