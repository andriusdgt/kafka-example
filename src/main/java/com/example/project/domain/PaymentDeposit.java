package com.example.project.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigInteger;
import java.util.Objects;

public class PaymentDeposit {

    private final String accountId;

    private final String currency;

    private final BigInteger sum;

    @JsonCreator
    public PaymentDeposit(@JsonProperty("account_id") String accountId,
                          @JsonProperty("currency") String currency,
                          @JsonProperty("sum") BigInteger sum) {
        this.accountId = accountId;
        this.currency = currency;
        this.sum = sum;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getCurrency() {
        return currency;
    }

    public BigInteger getSum() {
        return sum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, currency, sum);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentDeposit that = (PaymentDeposit) o;
        return Objects.equals(accountId, that.accountId) && Objects.equals(currency, that.currency) && Objects.equals(sum, that.sum);
    }

    @Override
    public String toString() {
        return "PaymentDeposit{" +
                "accountId='" + accountId + '\'' +
                ", currency='" + currency + '\'' +
                ", sum=" + sum +
                '}';
    }

}
