package com.walnet.backend.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class MemberInfoResponse {
    @Schema(description = "사용자 이름", example = "라아무개", required = true)
    String name;
    @Schema(description = "사용자 이메일", example = "user@example.com", required = true)
    String email;
    @Schema(description = "지갑 리스트", required = true)
    List<WalletInfo> wallets;
}
