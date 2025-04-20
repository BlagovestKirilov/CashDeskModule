package com.fibank.cashdeskmodule.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class TransactionDTO {
    private String cashierName;
    private String operationType;
    private String currency;
    private BigDecimal amount;
    private List<DenominationDTO> denominations;
    private LocalDateTime timestamp;

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
