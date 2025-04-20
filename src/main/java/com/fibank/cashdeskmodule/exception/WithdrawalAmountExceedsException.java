package com.fibank.cashdeskmodule.exception;

import com.fibank.cashdeskmodule.enums.ErrorCode;

import static com.fibank.cashdeskmodule.constant.Constants.WITHDRAWAL_AMOUNT_EXCEEDS_MESSAGE;

public class WithdrawalAmountExceedsException extends BaseException {
    public WithdrawalAmountExceedsException(ErrorCode errorCode) {
        super(errorCode);
    }

    public WithdrawalAmountExceedsException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public WithdrawalAmountExceedsException() {
        super(ErrorCode.WITHDRAWAL_AMOUNT_EXCEEDS, WITHDRAWAL_AMOUNT_EXCEEDS_MESSAGE);
    }
}
