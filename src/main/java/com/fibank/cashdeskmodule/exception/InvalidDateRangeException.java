package com.fibank.cashdeskmodule.exception;

import com.fibank.cashdeskmodule.enums.ErrorCode;

import static com.fibank.cashdeskmodule.constant.Constants.INVALID_DATE_RANGE_MESSAGE;

public class InvalidDateRangeException extends BaseException {
    public InvalidDateRangeException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidDateRangeException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public InvalidDateRangeException() {
        super(ErrorCode.INVALID_DATE_RANGE, INVALID_DATE_RANGE_MESSAGE);
    }
}
