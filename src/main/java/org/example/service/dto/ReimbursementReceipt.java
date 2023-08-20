package org.example.service.dto;

import java.math.BigDecimal;

public class ReimbursementReceipt {

    private final String name;
    private final BigDecimal value;

    public ReimbursementReceipt(String name, BigDecimal value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getValue() {
        return value;
    }
}
