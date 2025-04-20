package com.fibank.cashdeskmodule.enums;

public enum TransactionStatus {
    SUCCESSFUL_TRANSACTION(200), FAILED_TRANSACTION(400);

    TransactionStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    private final int statusCode;

    public int getStatusCode() {
        return statusCode;
    }
}
