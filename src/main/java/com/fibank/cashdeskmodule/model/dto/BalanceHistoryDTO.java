package com.fibank.cashdeskmodule.model.dto;

import java.time.LocalDateTime;
import java.util.List;

public class BalanceHistoryDTO {
    private List<BalanceDTO> balances;
    private LocalDateTime updatedOn;

    public List<BalanceDTO> getBalances() {
        return balances;
    }

    public void setBalanceHistory(List<BalanceDTO> balances) {
        this.balances = balances;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }
}
