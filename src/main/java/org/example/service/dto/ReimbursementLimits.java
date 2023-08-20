package org.example.service.dto;

import java.math.BigDecimal;

public class ReimbursementLimits {

    private final BigDecimal totalReimbursement;
    private final int distance;
    private final int days;

    public ReimbursementLimits(BigDecimal totalReimbursement, int distance, int days) {
        this.totalReimbursement = totalReimbursement;
        this.distance = distance;
        this.days = days;
    }

    public BigDecimal getTotalReimbursement() {
        return totalReimbursement;
    }

    public int getDistance() {
        return distance;
    }

    public int getDays() {
        return days;
    }

}
