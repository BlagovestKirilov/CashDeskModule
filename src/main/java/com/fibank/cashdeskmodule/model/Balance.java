package com.fibank.cashdeskmodule.model;

import com.fibank.cashdeskmodule.enums.CurrencyType;

import java.math.BigDecimal;
import java.util.List;

public class Balance {

    private CurrencyType currency;

    private BigDecimal amount;

    private List<Denomination> denominations;

    public CurrencyType getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyType currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public List<Denomination> getDenominations() {
        return denominations;
    }

    public void setDenominations(List<Denomination> denominations) {
        this.denominations = denominations;
    }
}
