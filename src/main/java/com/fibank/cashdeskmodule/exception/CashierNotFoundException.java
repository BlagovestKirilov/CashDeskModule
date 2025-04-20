package com.fibank.cashdeskmodule.exception;

import com.fibank.cashdeskmodule.enums.ErrorCode;

import static com.fibank.cashdeskmodule.constant.Constants.CASHIER_NOT_FOUND_MESSAGE;

public class CashierNotFoundException extends BaseException {

    public CashierNotFoundException() {
        super(ErrorCode.CASHIER_NOT_FOUND, CASHIER_NOT_FOUND_MESSAGE);
    }

    public CashierNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public CashierNotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
