package com.fibank.cashdeskmodule.service.impl;

import com.fibank.cashdeskmodule.model.dto.DenominationDTO;
import com.fibank.cashdeskmodule.model.dto.TransactionDTO;
import com.fibank.cashdeskmodule.model.dto.request.CashOperationRequest;
import com.fibank.cashdeskmodule.model.dto.response.CashOperationResponse;
import com.fibank.cashdeskmodule.enums.CurrencyType;
import com.fibank.cashdeskmodule.enums.DenominationValue;
import com.fibank.cashdeskmodule.enums.OperationType;
import com.fibank.cashdeskmodule.enums.TransactionStatus;
import com.fibank.cashdeskmodule.exception.*;
import com.fibank.cashdeskmodule.model.Balance;
import com.fibank.cashdeskmodule.model.Cashier;
import com.fibank.cashdeskmodule.model.Denomination;
import com.fibank.cashdeskmodule.model.Transaction;
import com.fibank.cashdeskmodule.repository.CashierRepository;
import com.fibank.cashdeskmodule.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.fibank.cashdeskmodule.constant.Constants.SUCCESSFUL_TRANSACTION_MESSAGE;

@Service
public class CashOperationServiceImpl implements com.fibank.cashdeskmodule.service.CashOperationService {

    private final TransactionRepository transactionRepository;
    private final CashierRepository cashierRepository;
    private static final Logger logger = LoggerFactory.getLogger(CashOperationServiceImpl.class);

    @Autowired
    public CashOperationServiceImpl(TransactionRepository transactionRepository, CashierRepository cashierRepository) {
        this.transactionRepository = transactionRepository;
        this.cashierRepository = cashierRepository;
    }

    public CashOperationResponse cashOperation(CashOperationRequest request) {
        try {
            validateDenominations(request);
            Transaction transaction = processCashOperation(request);
            return generateCashOperationResponse(transaction, SUCCESSFUL_TRANSACTION_MESSAGE);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error in cash operation {}", e.getMessage());
            Transaction transaction = generateTransaction(request, TransactionStatus.FAILED_TRANSACTION);
            transactionRepository.save(transaction);
            return generateCashOperationResponse(transaction, e.getMessage());
        }
    }

    private Transaction processCashOperation(CashOperationRequest request) {
        logger.info("Received cash operation request: {}", request.toString());

        Cashier cashier = cashierRepository.findByName(request.getCashierName())
                .orElseThrow(() -> {
                    logger.error("Cashier not found: {}", request.getCashierName());
                    return new CashierNotFoundException();
                });

        Balance balance = cashier.getBalances().stream()
                .filter(b -> b.getCurrency().toString().equals(request.getCurrency())).findFirst()
                .orElseThrow(() -> {
                    logger.error("Balance not found for currency: {}", request.getCurrency());
                    return new BalanceNotFoundException();
                });

        if (request.getOperationType().equals(OperationType.DEPOSIT.toString())) {
            return processDeposit(request, cashier, balance);

        } else {
            return processWithdraw(request, cashier, balance);

        }
    }

    private Transaction processDeposit(CashOperationRequest request, Cashier cashier, Balance balance) {
        logger.info("Processing deposit for cashier: {}", request.getCashierName());

        for (DenominationDTO denominationDTO : request.getDenominations()) {
            Denomination denomination = findDenominationByValue(balance.getDenominations(), denominationDTO.getDenominationValue());
            denomination.setQuantity(denomination.getQuantity() + denominationDTO.getQuantity());
            logger.debug("Deposited {} of {}", denominationDTO.getQuantity(), denominationDTO.getDenominationValue());
        }

        balance.setAmount(balance.getAmount().add(request.getAmount()));
        cashier.setLastUpdate(LocalDateTime.now());

        Transaction transaction = generateTransaction(request, TransactionStatus.SUCCESSFUL_TRANSACTION);
        transactionRepository.save(transaction);
        cashierRepository.save(cashier);

        logger.info("Deposit successful for cashier: {}", request.getCashierName());
        return transaction;
    }

    private Transaction processWithdraw(CashOperationRequest request, Cashier cashier, Balance balance) {
        logger.info("Processing withdrawal for cashier: {}", request.getCashierName());

        if (balance.getAmount().compareTo(request.getAmount()) > 0) {
            for (DenominationDTO denominationDTO : request.getDenominations()) {
                Denomination denomination = findDenominationByValue(balance.getDenominations(), denominationDTO.getDenominationValue());
                int newDenominationQuantity = denomination.getQuantity() - denominationDTO.getQuantity();

                if (newDenominationQuantity >= 0) {
                    denomination.setQuantity(newDenominationQuantity);
                    logger.debug("Withdrew {} of {}", denominationDTO.getQuantity(), denominationDTO.getDenominationValue());
                } else {
                    logger.error("Not enough denomination quantity of {}", denominationDTO.getDenominationValue());
                    throw new NotEnoughDenominationException(denominationDTO.getDenominationValue());
                }
            }

            balance.setAmount(balance.getAmount().subtract(request.getAmount()));
            cashier.setLastUpdate(LocalDateTime.now());

            Transaction transaction = generateTransaction(request, TransactionStatus.SUCCESSFUL_TRANSACTION);
            transactionRepository.save(transaction);
            cashierRepository.save(cashier);

            logger.info("Withdrawal successful for cashier: {}", request.getCashierName());
            return transaction;

        } else {
            logger.error("Withdrawal amount exceeds balance: Requested {}, Available {}", request.getAmount(), balance.getAmount());
            throw new WithdrawalAmountExceedsException();
        }
    }

    private Transaction generateTransaction(CashOperationRequest request, TransactionStatus transactionStatus) {
        logger.debug("Generating transaction for request");

        Transaction transaction = new Transaction();
        transaction.setTransactionStatus(transactionStatus);
        transaction.setAmount(request.getAmount());
        transaction.setCurrency(CurrencyType.valueOf(request.getCurrency()));
        transaction.setCashierName(request.getCashierName());
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setOperationType(OperationType.valueOf(request.getOperationType()));

        List<Denomination> denominations = request.getDenominations().stream()
                .map(dto -> {
                    Denomination denomination = new Denomination();
                    denomination.setQuantity(dto.getQuantity());
                    denomination.setDenominationValue(DenominationValue.valueOf(dto.getDenominationValue()));
                    return denomination;
                })
                .collect(Collectors.toList());

        transaction.setDenominations(denominations);

        logger.debug("Transaction generated successfully");
        return transaction;
    }

    private CashOperationResponse generateCashOperationResponse(Transaction transaction, String message) {
        CashOperationResponse cashOperationResponse = new CashOperationResponse();
        cashOperationResponse.setStatus(transaction.getTransactionStatus());
        cashOperationResponse.setMessage(message);


        TransactionDTO transactionDTO = new TransactionDTO();
        List<DenominationDTO> denominations = transaction.getDenominations().stream()
                .map(denomination -> {
                    DenominationDTO denominationDTO = new DenominationDTO();
                    denominationDTO.setQuantity(denomination.getQuantity());
                    denominationDTO.setDenominationValue(denomination.getDenominationValue().toString());
                    return denominationDTO;
                })
                .collect(Collectors.toList());
        transactionDTO.setDenominations(denominations);
        transactionDTO.setAmount(transaction.getAmount());
        transactionDTO.setCurrency(transaction.getCurrency().toString());
        transactionDTO.setOperationType(transaction.getOperationType().toString());
        transactionDTO.setTimestamp(transaction.getTimestamp());
        transactionDTO.setCashierName(transaction.getCashierName());
        cashOperationResponse.setTransaction(transactionDTO);
        return cashOperationResponse;
    }

    private Denomination findDenominationByValue(List<Denomination> denominations, String denominationValue) {
        return denominations.stream()
                .filter(d -> d.getDenominationValue().toString().equals(denominationValue)).findFirst()
                .orElseThrow(() -> {
                    logger.error("Denomination not found: {}", denominationValue);
                    return new DenominationNotFoundException();
                });
    }

    private void validateDenominations(CashOperationRequest request) {
        List<DenominationDTO> denominations = request.getDenominations();
        BigDecimal amount = request.getAmount();

        BigDecimal expectedAmount = BigDecimal.ZERO;
        for (DenominationDTO dto : denominations) {
            expectedAmount = expectedAmount.add(DenominationValue.valueOf(dto.getDenominationValue()).getValue()
                    .multiply(BigDecimal.valueOf(dto.getQuantity())));
        }

        if (expectedAmount.compareTo(amount) != 0) {
            throw new DenominationNotValidException();
        }
    }
}
