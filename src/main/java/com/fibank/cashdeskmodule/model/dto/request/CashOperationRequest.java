package com.fibank.cashdeskmodule.model.dto.request;

import com.fibank.cashdeskmodule.model.dto.DenominationDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

import static com.fibank.cashdeskmodule.constant.Constants.*;

public class CashOperationRequest {
    @NotBlank(message = CASHIER_NAME_REQUIRED)
    private String cashierName;

    @NotBlank(message = OPERATION_TYPE_REQUIRED)
    @Pattern(regexp = "^(DEPOSIT|WITHDRAWAL)$", message = NOT_SUPPORTED_OPERATION_MESSAGE)
    private String operationType;

    @NotBlank(message = CURRENCY_REQUIRED)
    @Pattern(regexp = "^(BGN|EUR)$", message = CURRENCY_NOT_SUPPORTED)
    private String currency;

    @Positive(message = AMOUNT_POSITIVE)
    private BigDecimal amount;

    @NotNull(message = DENOMINATIONS_REQUIRED)
    @Size(min = 1, message = MIN_ONE_DENOMINATION)
    private List<@Valid DenominationDTO> denominations;

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

    @Override
    public String toString() {
        return "CashOperationRequest{" +
                "cashierName='" + cashierName + '\'' +
                ", operationType='" + operationType + '\'' +
                ", currency='" + currency + '\'' +
                ", amount=" + amount +
                ", denominations=" + denominations.toString() +
                '}';
    }
}
