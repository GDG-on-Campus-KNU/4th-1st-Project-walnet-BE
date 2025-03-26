package com.walnet.backend.domain.transaction.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("TRANSFER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transfer extends Transaction{
    @Column(name = "transaction_account_number")
    private String accountNumber;
}
