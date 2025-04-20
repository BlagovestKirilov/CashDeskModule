package com.fibank.cashdeskmodule.enums;

import static com.fibank.cashdeskmodule.constant.Constants.*;

public enum ErrorCode {
    CASHIER_NOT_FOUND(404, CASHIER_NOT_FOUND_MESSAGE),
    BALANCE_NOT_FOUND(404, BALANCE_NOT_FOUND_MESSAGE),
    DENOMINATION_NOT_FOUND(404, DENOMINATION_NOT_FOUND_MESSAGE),
    INVALID_DATE_RANGE(400, INVALID_DATE_RANGE_MESSAGE),
    NOT_ENOUGH_DENOMINATION(400, NOT_ENOUGH_DENOMINATION_MESSAGE),
    WITHDRAWAL_AMOUNT_EXCEEDS(400, WITHDRAWAL_AMOUNT_EXCEEDS_MESSAGE),
    DENOMINATION_NOT_VALID(400, DENOMINATION_NOT_VALID_MESSAGE);

    private final int statusCode;
    private final String message;

    ErrorCode(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
