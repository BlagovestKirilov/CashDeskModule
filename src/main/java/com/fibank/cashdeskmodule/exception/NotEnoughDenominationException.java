package com.fibank.cashdeskmodule.exception;

import com.fibank.cashdeskmodule.enums.ErrorCode;

import static com.fibank.cashdeskmodule.constant.Constants.NOT_ENOUGH_DENOMINATION_MESSAGE;

public class NotEnoughDenominationException extends BaseException {
    public NotEnoughDenominationException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NotEnoughDenominationException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public NotEnoughDenominationException(String denominationValue) {
        super(ErrorCode.NOT_ENOUGH_DENOMINATION, String.format(NOT_ENOUGH_DENOMINATION_MESSAGE, denominationValue));
    }
}
