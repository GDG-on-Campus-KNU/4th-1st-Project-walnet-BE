package com.walnet.backend.domain.transaction.entity;

import com.walnet.backend.domain.wallet.entity.CurrencyEnum;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("EXCHANGE")
public class Exchange extends Transaction{
    @Enumerated(EnumType.STRING)
    private CurrencyEnum counterCurrency;

    private String exchangeRate;
}
