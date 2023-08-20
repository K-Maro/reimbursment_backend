package org.example.repository;

import org.example.service.dto.ReimbursementReceipt;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class UserReimbursementProperties {

    private final List<ReimbursementReceipt> receipts;
    private final int carMillage;
    private final List<LocalDate> days;
    private final BigDecimal reimbursementValue;

    public UserReimbursementProperties(List<ReimbursementReceipt> receipts, int carMillage, List<LocalDate> days, BigDecimal reimbursementValue) {
        this.receipts = receipts;
        this.carMillage = carMillage;
        this.days = days;
        this.reimbursementValue = reimbursementValue;
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

    public BigDecimal getReimbursementValue() {
        return reimbursementValue;
    }

}
