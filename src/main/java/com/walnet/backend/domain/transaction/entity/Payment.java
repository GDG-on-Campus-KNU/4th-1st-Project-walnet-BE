package com.walnet.backend.domain.transaction.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("PAYMENT")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment extends Transaction{
    @NotBlank
    private String merchantName;
}
