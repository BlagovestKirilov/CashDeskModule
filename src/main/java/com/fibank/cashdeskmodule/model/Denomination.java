package com.fibank.cashdeskmodule.model;

import com.fibank.cashdeskmodule.enums.DenominationValue;

public class Denomination {
    private DenominationValue denominationValue;
    private Integer quantity;

    public DenominationValue getDenominationValue() {
        return denominationValue;
    }

    public void setDenominationValue(DenominationValue denominationValue) {
        this.denominationValue = denominationValue;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
