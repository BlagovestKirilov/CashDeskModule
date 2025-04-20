package com.fibank.cashdeskmodule.service.impl;

import com.fibank.cashdeskmodule.model.dto.BalanceDTO;
import com.fibank.cashdeskmodule.model.dto.BalanceHistoryDTO;
import com.fibank.cashdeskmodule.model.dto.DenominationDTO;
import com.fibank.cashdeskmodule.model.dto.request.CashBalanceRequest;
import com.fibank.cashdeskmodule.model.dto.response.CashBalanceResponse;
import com.fibank.cashdeskmodule.exception.CashierNotFoundException;
import com.fibank.cashdeskmodule.exception.InvalidDateRangeException;
import com.fibank.cashdeskmodule.model.Balance;
import com.fibank.cashdeskmodule.model.Cashier;
import com.fibank.cashdeskmodule.repository.CashierRepository;
import com.fibank.cashdeskmodule.service.CashBalanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CashBalanceServiceImpl implements CashBalanceService {

    private final CashierRepository cashierRepository;
    private static final Logger logger = LoggerFactory.getLogger(CashBalanceServiceImpl.class);

    @Autowired
    public CashBalanceServiceImpl(CashierRepository cashierRepository) {
        this.cashierRepository = cashierRepository;
    }

    public List<CashBalanceResponse> getCashBalance(CashBalanceRequest request) {
        logger.info("Received request to get cash balance: {}", request);
        validateCashBalanceRequest(request);

        Map<String, CashBalanceResponse> cashBalanceResponseMap = new HashMap<>();
        List<Cashier> cashiers = cashierRepository.findByRequest(request);
        logger.info("Found {} cashiers matching request", cashiers.size());

        for (Cashier cashier : cashiers) {
            logger.debug("Processing cashier: {}", cashier.getName());

            if (!cashBalanceResponseMap.containsKey(cashier.getName())) {
                CashBalanceResponse cashBalanceResponse = new CashBalanceResponse();
                cashBalanceResponse.setCashier(cashier.getName());
                cashBalanceResponse.setBalanceHistory(new ArrayList<>());
                cashBalanceResponseMap.put(cashier.getName(), cashBalanceResponse);
                logger.debug("Created new CashBalanceResponse for cashier {}", cashier.getName());
            }


            BalanceHistoryDTO balanceHistoryDTO = new BalanceHistoryDTO();
            balanceHistoryDTO.setUpdatedOn(cashier.getLastUpdate());
            balanceHistoryDTO.setBalanceHistory(new ArrayList<>());

            for (Balance balance : cashier.getBalances()) {
                BalanceDTO balanceDTO = new BalanceDTO();
                balanceDTO.setAmount(balance.getAmount());
                balanceDTO.setCurrency(balance.getCurrency().toString());
                List<DenominationDTO> denominations = balance.getDenominations().stream()
                        .map(denomination -> {
                            DenominationDTO denominationDTO = new DenominationDTO();
                            denominationDTO.setQuantity(denomination.getQuantity());
                            denominationDTO.setDenominationValue(denomination.getDenominationValue().toString());
                            return denominationDTO;
                        })
                        .collect(Collectors.toList());
                balanceDTO.setDenominations(denominations);
                balanceHistoryDTO.getBalances().add(balanceDTO);

            }
            cashBalanceResponseMap.get(cashier.getName()).getBalanceHistory().add(balanceHistoryDTO);
        }

        logger.info("Returning cash balance response");
        return cashBalanceResponseMap.values().stream().toList();
    }

    private void validateCashBalanceRequest(CashBalanceRequest request) {
        logger.debug("Validating cash balance request");

        if (request.getCashierName() != null) {
            cashierRepository.findByName(request.getCashierName())
                    .orElseThrow(() -> {
                        logger.error("Cashier not found: {}", request.getCashierName());
                        return new CashierNotFoundException();
                    });
        }

        if (request.getDateFrom() != null && request.getDateTo() != null
                && request.getDateFrom().isAfter(request.getDateTo())) {
            logger.error("Date from {} is after date to {}", request.getDateFrom(), request.getDateTo());
            throw new InvalidDateRangeException();
        }
    }
}
