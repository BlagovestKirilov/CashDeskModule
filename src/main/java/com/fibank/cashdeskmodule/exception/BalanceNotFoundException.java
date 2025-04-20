package com.fibank.cashdeskmodule.exception;

import com.fibank.cashdeskmodule.enums.ErrorCode;

import static com.fibank.cashdeskmodule.constant.Constants.BALANCE_NOT_FOUND_MESSAGE;

public class BalanceNotFoundException extends BaseException {
    public BalanceNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public BalanceNotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public BalanceNotFoundException() {
        super(ErrorCode.BALANCE_NOT_FOUND, BALANCE_NOT_FOUND_MESSAGE);
    }
}
