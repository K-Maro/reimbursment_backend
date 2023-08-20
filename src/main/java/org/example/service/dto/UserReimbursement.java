package org.example.service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class UserReimbursement {

    private final List<ReimbursementReceipt> receipts;
    private final int carMillage;
    private final List<LocalDate> days;
    private final BigDecimal reimbursementValue;

    public UserReimbursement(List<ReimbursementReceipt> receipts, int carMillage, List<LocalDate> days, BigDecimal reimbursementValue) {
        this.receipts = receipts;
        this.carMillage = carMillage;
        this.days = days;
        this.reimbursementValue = reimbursementValue;
    }

    public BigDecimal getReimbursementValue() {
        return reimbursementValue;
    }

    public List<ReimbursementReceipt> getReceipts() {
        return receipts;
    }

    public int getCarMillage() {
        return carMillage;
    }

    public List<LocalDate> getDays() {
        return days;
    }

    public LocalDate getStartDay() {
        return days.stream()
                .min(Comparator.comparing(LocalDate::toEpochDay))
                .get();
    }

    public LocalDate getEndDay() {
        return days.stream()
                .max(Comparator.comparing(LocalDate::toEpochDay))
                .get();
    }
}