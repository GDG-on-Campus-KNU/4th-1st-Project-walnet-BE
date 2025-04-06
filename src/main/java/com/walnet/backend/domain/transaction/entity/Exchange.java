package com.walnet.backend.domain.transaction.entity;

import com.walnet.backend.domain.wallet.entity.CurrencyEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("EXCHANGE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Exchange extends Transaction{
    @Enumerated(EnumType.STRING)
    private CurrencyEnum counterCurrency;

    private String exchangeRate;
}
