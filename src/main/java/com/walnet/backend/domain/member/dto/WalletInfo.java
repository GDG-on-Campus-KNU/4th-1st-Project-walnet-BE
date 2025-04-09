package com.walnet.backend.domain.member.dto;

import com.walnet.backend.domain.wallet.entity.CurrencyEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class WalletInfo {
    @Schema(description = "wallet 아이디", example = "1", required = true)
    Long id;
    @Schema(description = "wallet 통화 3자리 코드", example = "KRW", required = true)
    CurrencyEnum currency;
    @Schema(description = "wallet 잔액", example = "9999", required = true)
    Long balance;
}
