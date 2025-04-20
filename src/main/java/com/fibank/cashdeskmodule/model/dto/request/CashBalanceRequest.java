package com.fibank.cashdeskmodule.model.dto.request;

import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public class CashBalanceRequest {

    @PastOrPresent
    private LocalDate dateFrom;

    @PastOrPresent
    private LocalDate dateTo;

    private String cashierName;

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }
}
