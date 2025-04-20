package com.fibank.cashdeskmodule.model.dto.response;

import com.fibank.cashdeskmodule.model.dto.BalanceHistoryDTO;

import java.util.List;

public class CashBalanceResponse {
    private String cashier;

    private List<BalanceHistoryDTO> balanceHistory;

    public String getCashier() {
        return cashier;
    }

    public void setCashier(String cashier) {
        this.cashier = cashier;
    }

    public List<BalanceHistoryDTO> getBalanceHistory() {
        return balanceHistory;
    }

    public void setBalanceHistory(List<BalanceHistoryDTO> balanceHistory) {
        this.balanceHistory = balanceHistory;
    }
}
