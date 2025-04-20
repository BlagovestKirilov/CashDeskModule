package com.fibank.cashdeskmodule.model.dto;

import java.math.BigDecimal;
import java.util.List;

public class BalanceDTO {
    private String currency;

    private BigDecimal amount;

    private List<DenominationDTO> denominations;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public List<DenominationDTO> getDenominations() {
        return denominations;
    }

    public void setDenominations(List<DenominationDTO> denominations) {
        this.denominations = denominations;
    }
}
