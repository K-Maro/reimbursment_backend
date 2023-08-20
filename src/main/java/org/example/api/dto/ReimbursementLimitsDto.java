package org.example.api.dto;

import java.math.BigDecimal;

public class ReimbursementLimitsDto {

    private BigDecimal totalReimbursement;
    private int distance;
    private int days;

    public ReimbursementLimitsDto(BigDecimal totalReimbursement, int distance, int days) {
        this.totalReimbursement = totalReimbursement;
        this.distance = distance;
        this.days = days;
    }

    public ReimbursementLimitsDto() {
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public BigDecimal getTotalReimbursement() {
        return totalReimbursement;
    }

    public void setTotalReimbursement(BigDecimal totalReimbursement) {
        this.totalReimbursement = totalReimbursement;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
