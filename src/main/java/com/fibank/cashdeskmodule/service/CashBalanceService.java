package com.fibank.cashdeskmodule.service;

import com.fibank.cashdeskmodule.model.dto.request.CashBalanceRequest;
import com.fibank.cashdeskmodule.model.dto.response.CashBalanceResponse;

import java.util.List;

public interface CashBalanceService {
    List<CashBalanceResponse> getCashBalance(CashBalanceRequest request);
}
