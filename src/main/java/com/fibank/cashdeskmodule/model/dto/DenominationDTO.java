package com.fibank.cashdeskmodule.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import static com.fibank.cashdeskmodule.constant.Constants.DENOMINATION_VALUE_REQUIRED;
import static com.fibank.cashdeskmodule.constant.Constants.QUANTITY_POSITIVE;

public class DenominationDTO {
    @NotBlank(message = DENOMINATION_VALUE_REQUIRED)
    private String denominationValue;

    @PositiveOrZero(message = QUANTITY_POSITIVE)
    private Integer quantity;

    public String getDenominationValue() {
        return denominationValue;
    }

    public void setDenominationValue(String denominationValue) {
        this.denominationValue = denominationValue;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "DenominationDTO{" +
                "denominationValue='" + denominationValue + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }
}
