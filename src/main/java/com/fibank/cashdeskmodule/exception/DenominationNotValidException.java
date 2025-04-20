package com.fibank.cashdeskmodule.exception;

import com.fibank.cashdeskmodule.enums.ErrorCode;

import static com.fibank.cashdeskmodule.constant.Constants.DENOMINATION_NOT_VALID_MESSAGE;

public class DenominationNotValidException extends BaseException {
    public DenominationNotValidException(ErrorCode errorCode) {
        super(errorCode);
    }

    public DenominationNotValidException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public DenominationNotValidException() {
        super(ErrorCode.NOT_ENOUGH_DENOMINATION, DENOMINATION_NOT_VALID_MESSAGE);
    }
}
