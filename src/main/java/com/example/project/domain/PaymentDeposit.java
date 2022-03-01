package com.example.project.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigInteger;
import java.util.Currency;

@Getter
@EqualsAndHashCode
@ToString
public class PaymentDeposit {

    private final String accountId;

    private final Currency currency;

    private final BigInteger sum;

    @JsonCreator
    public PaymentDeposit(@JsonProperty("account_id") String accountId,
                          @JsonProperty("currency") Currency currency,
                          @JsonProperty("sum") BigInteger sum) {
        this.accountId = accountId;
        this.currency = currency;
        this.sum = sum;
    }

}
