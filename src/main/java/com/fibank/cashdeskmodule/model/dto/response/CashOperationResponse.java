package com.fibank.cashdeskmodule.model.dto.response;

import com.fibank.cashdeskmodule.model.dto.TransactionDTO;
import com.fibank.cashdeskmodule.enums.TransactionStatus;

public class CashOperationResponse {
    private TransactionStatus status;

    private String message;

    private TransactionDTO transaction;

    public String getMessage() {
        return message;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TransactionDTO getTransaction() {
        return transaction;
    }

    public void setTransaction(TransactionDTO transaction) {
        this.transaction = transaction;
    }
}
