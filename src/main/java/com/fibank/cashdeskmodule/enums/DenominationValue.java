package com.fibank.cashdeskmodule.enums;

import com.fibank.cashdeskmodule.exception.DenominationNotFoundException;

import java.math.BigDecimal;

public enum DenominationValue {
    ONE_COIN("ONE_COIN", new BigDecimal("0.01")),
    TWO_COINS("TWO_COINS", new BigDecimal("0.02")),
    FIVE_COINS("FIVE_COINS", new BigDecimal("0.05")),
    TEN_COINS("TEN_COINS", new BigDecimal("0.10")),
    TWENTY_COINS("TWENTY_COINS", new BigDecimal("0.20")),
    FIFTY_COINS("FIFTY_COINS", new BigDecimal("0.50")),
    ONE_LEV("ONE_LEV", new BigDecimal("1.00")),
    TWO_LEVA("TWO_LEVA", new BigDecimal("2.00")),

    FIVE_LEVA("FIVE_LEVA", new BigDecimal("5.00")),
    TEN_LEVA("TEN_LEVA", new BigDecimal("10.00")),
    TWENTY_LEVA("TWENTY_LEVA", new BigDecimal("20.00")),
    FIFTY_LEVA("FIFTY_LEVA", new BigDecimal("50.00")),
    ONE_HUNDRED_LEVA("ONE_HUNDRED_LEVA", new BigDecimal("100.00")),


    ONE_CENT("ONE_CENT", new BigDecimal("0.01")),
    TWO_CENTS("TWO_CENTS", new BigDecimal("0.02")),
    FIVE_CENTS("FIVE_CENTS", new BigDecimal("0.05")),
    TEN_CENTS("TEN_CENTS", new BigDecimal("0.10")),
    TWENTY_CENTS("TWENTY_CENTS", new BigDecimal("0.20")),
    FIFTY_CENTS("FIFTY_CENTS", new BigDecimal("0.50")),
    ONE_EURO("ONE_EURO", new BigDecimal("1.00")),
    TWO_EURO("TWO_EURO", new BigDecimal("2.00")),

    FIVE_EURO("FIVE_EURO", new BigDecimal("5.00")),
    TEN_EURO("TEN_EURO", new BigDecimal("10.00")),
    TWENTY_EURO("TWENTY_EURO", new BigDecimal("20.00")),
    FIFTY_EURO("FIFTY_EURO", new BigDecimal("50.00")),
    ONE_HUNDRED_EURO("ONE_HUNDRED_EURO", new BigDecimal("100.00")),
    TWO_HUNDRED_EURO("TWO_HUNDRED_EURO", new BigDecimal("200.00")),
    FIVE_HUNDRED_EURO("FIVE_HUNDRED_EURO", new BigDecimal("500.00"));

    private final String code;
    private final BigDecimal value;

    DenominationValue(String code, BigDecimal value) {
        this.code = code;
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getCode() {
        return code;
    }

    public static final DenominationValue[] BGN_DENOMINATIONS = {
            DenominationValue.ONE_COIN,
            DenominationValue.TWO_COINS,
            DenominationValue.FIVE_COINS,
            DenominationValue.TEN_COINS,
            DenominationValue.TWENTY_COINS,
            DenominationValue.FIFTY_COINS,
            DenominationValue.ONE_LEV,
            DenominationValue.TWO_LEVA,
            DenominationValue.FIVE_LEVA,
            DenominationValue.TEN_LEVA,
            DenominationValue.TWENTY_LEVA,
            DenominationValue.FIFTY_LEVA,
            DenominationValue.ONE_HUNDRED_LEVA
    };

    public static final DenominationValue[] EUR_DENOMINATIONS = {
            DenominationValue.ONE_CENT,
            DenominationValue.TWO_CENTS,
            DenominationValue.FIVE_CENTS,
            DenominationValue.TEN_CENTS,
            DenominationValue.TWENTY_CENTS,
            DenominationValue.FIFTY_CENTS,
            DenominationValue.ONE_EURO,
            DenominationValue.TWO_EURO,
            DenominationValue.FIVE_EURO,
            DenominationValue.TEN_EURO,
            DenominationValue.TWENTY_EURO,
            DenominationValue.FIFTY_EURO,
            DenominationValue.ONE_HUNDRED_EURO,
            DenominationValue.TWO_HUNDRED_EURO,
            DenominationValue.FIVE_HUNDRED_EURO
    };
}
