package com.fibank.cashdeskmodule.service;

import com.fibank.cashdeskmodule.model.dto.request.CashOperationRequest;
import com.fibank.cashdeskmodule.model.dto.response.CashOperationResponse;

public interface CashOperationService {
    CashOperationResponse cashOperation(CashOperationRequest request);
}
