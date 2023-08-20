package org.example.api.dto;

import java.math.BigDecimal;

public class ReceiptDto {

    private String name;
    private BigDecimal value;

    public ReceiptDto(String name, BigDecimal value) {
        this.name = name;
        this.value = value;
    }

    public ReceiptDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
