package com.fibank.cashdeskmodule.exception;

import com.fibank.cashdeskmodule.enums.ErrorCode;

import static com.fibank.cashdeskmodule.constant.Constants.DENOMINATION_NOT_FOUND_MESSAGE;

public class DenominationNotFoundException extends BaseException {
    public DenominationNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public DenominationNotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public DenominationNotFoundException() {
        super(ErrorCode.DENOMINATION_NOT_FOUND, DENOMINATION_NOT_FOUND_MESSAGE);
    }
}
