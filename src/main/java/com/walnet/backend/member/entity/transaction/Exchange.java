package com.walnet.backend.member.entity.transaction;

import com.walnet.backend.member.entity.CurrencyEnum;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("EXCHANGE")
public class Exchange extends Transaction{
    @Enumerated(EnumType.STRING)
    private CurrencyEnum counterCurrency;

    private String exchangeRate;
}
