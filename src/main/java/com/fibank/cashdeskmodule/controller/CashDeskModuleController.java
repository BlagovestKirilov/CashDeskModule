package com.fibank.cashdeskmodule.controller;

import com.fibank.cashdeskmodule.model.dto.request.CashBalanceRequest;
import com.fibank.cashdeskmodule.model.dto.request.CashOperationRequest;
import com.fibank.cashdeskmodule.model.dto.response.CashBalanceResponse;
import com.fibank.cashdeskmodule.model.dto.response.CashOperationResponse;
import com.fibank.cashdeskmodule.service.CashBalanceService;
import com.fibank.cashdeskmodule.service.CashOperationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class CashDeskModuleController {

    private final CashBalanceService cashBalanceService;
    private final CashOperationService cashOperationService;

    @Autowired
    public CashDeskModuleController(CashBalanceService cashBalanceService, CashOperationService cashOperationService) {
        this.cashBalanceService = cashBalanceService;
        this.cashOperationService = cashOperationService;
    }

    @GetMapping("/cash-balance")
    public ResponseEntity<List<CashBalanceResponse>> getCashBalance(@Valid @RequestBody CashBalanceRequest request) {
        List<CashBalanceResponse> response = cashBalanceService.getCashBalance(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/cash-operation")
    public ResponseEntity<CashOperationResponse> cashOperation(@Valid @RequestBody CashOperationRequest request) {
        CashOperationResponse response = cashOperationService.cashOperation(request);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus().getStatusCode()));
    }
}
